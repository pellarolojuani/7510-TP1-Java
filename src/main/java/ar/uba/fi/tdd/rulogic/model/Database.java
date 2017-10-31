package ar.uba.fi.tdd.rulogic.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpellarolo
 */
public class Database {
    private List<String> facts;
    private List<String> rules;
    
    public Database() {
        this.facts = new ArrayList<String>();
        this.rules = new ArrayList<String>();
    }
    
    
    
    private boolean validateFactSyntax(String query) {
        boolean result = true;
        if (query.equals("")) {
            result = false;
        } else if (query.indexOf("(") == -1) {
            result = false;
        } else if (query.indexOf(")") == -1) {
            result = false;
        } else if (query.charAt(query.length()-1) != '.') {     //TODO: revisar esto!
            result = false;
        }
        return result;
    }
    
    public int countFactsFromRule(String ruleFacts) {
        return ruleFacts.split("\\(").length;
    }
    
    public String getRuleByName(String name) {
        String rule = null;
        for (String oneRule : this.rules) {
            if (this.getRuleNameFromQuery(oneRule).equals(name)) {
                rule = oneRule;
                break;
            }
        }
        return rule;
    }
    
    public boolean analizeFacts(String query) {
        for (String fact : this.facts) {
            if (fact.equals(query)) {
                return true;
            }
        }
        return false;
    }
    
    public String[] getParamsFromFact(String query) {
        String subquery = query.substring(query.indexOf("(" + 1, query.indexOf(")")));
        return subquery.split(",");
    }
    
    private void addQuery(String query) {
        query = query.trim().replace(".", "");
        if (query.indexOf(":-") == -1) {
            this.setFact(query.replaceAll(" ", ""));
        } else {
            this.setRule(query.replaceAll(" ", ""));
        }
    }
    
    public void loadDatabase(List<String> database) {
        for (String query : database) {
            if (validateFactSyntax(query)) {
                addQuery(query);
            }
        }
    }
    
    public List<String> getFacts() {
        return facts;
    }

    public void setFacts(List<String> facts) {
        this.facts = facts;
    }

    public List<String> getRules() {
        return rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }
    
    public void setFact(String fact) {
        this.facts.add(fact);
    }
    
    public void setRule(String rule) {
        this.rules.add(rule);
    }
    
    private String getRuleNameFromQuery(String query) {
        return query.substring(0, query.indexOf("("));
    }
}
