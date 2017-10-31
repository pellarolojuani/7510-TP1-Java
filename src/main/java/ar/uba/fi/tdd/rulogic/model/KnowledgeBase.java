package ar.uba.fi.tdd.rulogic.model;

import java.util.List;

public class KnowledgeBase {
        private Database database = new Database();
        
        public void parseDatabase(List<String> database) {
            this.database.loadDatabase(database);
        }
        
	public boolean answer(String query) {
		return true;
	}

}
