package ar.uba.fi.tdd.rulogic.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeBase {

    private Database database = new Database();

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }
    
    public boolean evaluateQuerycore(String query) {
        boolean result = false;
        if (database.getFacts().size() != 0) {
            if (database.getRules().size() != 0) {
                if (query == "" || query == null) {
                    return result;
                }
                result = database.analizeFacts(query.replaceAll(" ", ""));
                if (result) {
                    return result;
                } else {
                    result = database.analizeRules(query);
                    return result;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void parseDatabase() throws FileNotFoundException, IOException {
        List<String> database = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/rules.db"));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\t", "");
            line = line.replaceAll(" ", "");
            database.add(line);
        }
        reader.close();

        this.database.loadDatabase(database);
    }

    public boolean answer(String query) {
        query = query.replaceAll("\t", "");
        query = query.replaceAll(" ", "");
        query = query.replaceAll("\\.", "");
        return evaluateQuerycore(query);
    }

}
