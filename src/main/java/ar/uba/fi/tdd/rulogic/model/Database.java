package ar.uba.fi.tdd.rulogic.model;

import java.util.ArrayList;
import java.util.Arrays;
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

    public List<String> getNewFactsFromRule(String rule, String query) {
        if (rule != null) {
            List<String> ruleParts = Arrays.asList(rule.split(":-"));
            List<String> queryParam = Arrays.asList(getParamsFromFact(query));
            List<String> ruleParam = Arrays.asList(getParamsFromFact(ruleParts.get(0)));
            String result = ruleParts.get(1);
            for (int i = 0; i < ruleParam.size(); i++) {
                result = result.replaceAll(ruleParam.get(i), queryParam.get(ruleParam.indexOf(ruleParam.get(i))));
            }
            List<String> result2 = new ArrayList<String>();
            int newFactsCont = countFactsFromRule(result);
            while (newFactsCont > 0) {
                String aux = result.substring(0, result.indexOf(")") + 1);
                result2.add(aux.replaceAll(" ", ""));
                if (newFactsCont != 1) {
                    result = result.substring(result.indexOf(")") + 2);
                }
                newFactsCont--;
            }
            return result2;
        } else {
            return new ArrayList<String>();
        }
    }

    public boolean processRule(String rule, String query) {
        List<String> newFactsFromRule = getNewFactsFromRule(rule, query);
        return matchFacts(newFactsFromRule, this.facts);
    }

    public boolean analizeRules(String query) {
        String ruleName = getRuleNameFromQuery(query);
        String rule = getRuleByName(ruleName);
        if (rule == null) {
            return false;
        } else {
            return processRule(rule, query);
        }
    }

    public boolean matchFacts(List<String> newFactsFromRule, List<String> parsedFacts) {
        int matches = 0;
        if (newFactsFromRule.size() == 0) {
            return false;
        }
        for (int i = 0; i < newFactsFromRule.size(); i++) {
            if (parsedFacts.indexOf(newFactsFromRule.get(i).replaceAll(" ", "")) != -1) {
                matches++;
            }
        }
        if (matches == newFactsFromRule.size()) {
            return true;
        }
        return false;
    }

    private boolean validateFactSyntax(String query) {
        boolean result = true;
        query = query.replaceAll("\t", "");
        query = query.replaceAll(" ", "");
        if (query.equals("")) {
            result = false;
        } else if (query.indexOf("(") == -1) {
            result = false;
        } else if (query.indexOf(")") == -1) {
            result = false;
        } else if (query.charAt(query.length() - 1) != '.') {     //TODO: revisar esto!
            result = false;
        }
        return result;
    }

    public int countFactsFromRule(String ruleFacts) {
        int counter = 0;
        for (int i = 0; i < ruleFacts.length(); i++) {
            if (ruleFacts.charAt(i) == '(') {
                counter++;
            }
        }
        return counter;
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
        query = query.replaceAll(" ", "");
        String subquery = query.substring(query.indexOf("(") + 1, query.indexOf(")"));
        return subquery.split(",");
    }

    private void addQuery(String query) {
        query = query.trim().replace(".", "");
        if (!query.contains(":-")) {
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

    public String getRuleNameFromQuery(String query) {
        if (query.indexOf("(") != -1) {
            return query.substring(0, query.indexOf("("));
        }
        return "";
    }
}
