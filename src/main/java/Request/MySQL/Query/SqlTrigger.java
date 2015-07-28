package Request.MySQL.Query;

import Request.Analytics.Analytics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alco on 03/07/2015.
 *  Contain all Trigger Request
 */
public class SqlTrigger {
    public static final double REQUEST_NUMBER_VERSION_CHANGE = 5.1;
    private final static String TRIGGER = "TRIGGER";

    public static String getTrigger() {
        return TRIGGER;
    }

    /**
     * toStringGetAll concat drop and trigger request
     *
     * @param tableNameList
     * @param champNameList
     * @param conditionList
     * @param currentVersion
     * @return
     */
    public String toStringGetAll(List<String> tableNameList, List<String> champNameList, List<String> conditionList,  double currentVersion) {
        return createListSqlDropIfExist(tableNameList) + "\n" + createSqlTriggerList(tableNameList, champNameList, conditionList, currentVersion);
    }

    /**
     * createListSqlDropIfExist call and concat all drop request
     *
     * @param tableNameList
     * @return
     */
    public String createListSqlDropIfExist(List<String> tableNameList) {
        String chaine = "";
        List<String> generate = new ArrayList<String>();
        for (String aTableName : tableNameList) {
            boolean generateBool = true;
            for (String name : generate) {
                if (name.equals(aTableName)) {
                    generateBool = false;
                }
            }
            if (generateBool) {
                generate.add(aTableName);
                chaine = chaine + "\n" + getDropIfExist(aTableName);
            }
        }
        return chaine;
    }

    /***
     * createSqlTriggerList call and concat all Trigger request
     * @param tableNameList
     * @param champNameList
     * @param conditionList
     * @param currentVersion
     * @return
     */
    public String createSqlTriggerList(List<String> tableNameList, List<String> champNameList, List<String> conditionList, double currentVersion) {
        String mySqlChaine = "";
        if (currentVersion > REQUEST_NUMBER_VERSION_CHANGE) {
            List<String> tableGenerate = new ArrayList<String>();
            for (int i = 0; i < tableNameList.size(); i++) {
                mySqlChaine = getSignalTriggerRequest(tableNameList, champNameList, conditionList, mySqlChaine, tableGenerate, i);
            }
        } else {
            List<String> tableGenerate = new ArrayList<String>();
            for (int i = 0; i < tableNameList.size(); i++) {
                mySqlChaine = getOldRequestTrigger(tableNameList, champNameList, conditionList, mySqlChaine, tableGenerate, i);
            }
        }
        return mySqlChaine;
    }

    /***
     * getDropIfExist get a drop request!
     * @param triggerName
     * @return
     */
    protected String getDropIfExist(String triggerName) {
        String chaine =
                "drop trigger if exists " + triggerName + "_UPDATE;\n" +
                        "drop trigger if exists " + triggerName + "_INSERT;";
        return chaine;
    }

    /***
     * getOldRequestTrigger return mysql request trigger for above 5.1 version
     * @param tableNameList
     * @param champNameList
     * @param conditionList
     * @param mySqlChaine
     * @param tableGenerate
     * @param i
     * @return
     */
    private String getOldRequestTrigger(List<String> tableNameList, List<String> champNameList, List<String> conditionList, String mySqlChaine, List<String> tableGenerate, int i) {
        String tableName = tableNameList.get(i);
        String triggerRequete = "";
        int j = i;
        Analytics analytics = new Analytics();
        Boolean generate = analytics.tryOccurrenceNotExist(tableGenerate, tableName);

        if (generate && i <= tableNameList.size()) {
            tableGenerate.add(tableName);
            while (generate && j < tableNameList.size()) {
                if (tableName.equals(tableNameList.get(j))) {
                    triggerRequete =  triggerRequete + "\n" +getConditionTriggerOldString(champNameList, conditionList, j);
                    j++;
                } else {
                    generate = false;
                }
            }
            mySqlChaine = mySqlChaine + "\n" + getCorpRequest(tableNameList, i, triggerRequete);
        }
        return mySqlChaine;
    }

    /***
     * getSignalTriggerRequest return mysql request trigger
     * @param tableNameList
     * @param champNameList
     * @param conditionList
     * @param mySqlChaine
     * @param tableGenerate
     * @param i
     * @return
     */
    private String getSignalTriggerRequest(List<String> tableNameList, List<String> champNameList, List<String> conditionList, String mySqlChaine, List<String> tableGenerate, int i) {
        String tableName = tableNameList.get(i);
        String triggerRequete = "";
        int j = i;
        Analytics analytics = new Analytics();
        Boolean generate = analytics.tryOccurrenceNotExist(tableGenerate, tableName);

        if (generate && i <= tableNameList.size()) {
            tableGenerate.add(tableName);
            while (generate && j < tableNameList.size()) {
                if (tableName.equals(tableNameList.get(j))) {
                    triggerRequete =  triggerRequete + "\n" +getConditionTriggerString(champNameList, conditionList, j);
                    j++;
                } else {
                    generate = false;
                }
            }
            mySqlChaine = mySqlChaine + "\n" + getCorpRequest(tableNameList, i, triggerRequete);
        }
        return mySqlChaine;
    }

    /**
     * getCorpRequest corp of request trigger concat
     *
     * @param tableNameList
     * @param pos
     * @param triggerRequete
     * @return
     */
    private String getCorpRequest(List<String> tableNameList, int pos, String triggerRequete) {
        String mySqlChaine =
                "delimiter $$\n" +
                "create trigger " + tableNameList.get(pos).trim() + "_UPDATE BEFORE UPDATE on " + tableNameList.get(pos).trim() + "\n" +
                "\tfor each row\n" +
                        "\t\tbegin" +
                triggerRequete + "\n" +
                "end;\n" +
                "$$\n" +
                "\n" +
                "delimiter $$\n" +
                "create trigger " + tableNameList.get(pos).trim() + "_INSERT BEFORE INSERT on " + tableNameList.get(pos).trim() + "\n" +
                "\tfor each row\n" +
                        "\t\tbegin" +
                triggerRequete + "\n" +
                "end;\n" +
                "$$";
        return mySqlChaine;
    }

    /***
     * getConditionTriggerString return condition with signal
     * @param champNameList
     * @param conditionList
     * @param pos
     * @return
     */
    private String getConditionTriggerString(List<String> champNameList, List<String> conditionList, int pos) {
        String ifRequete = "\t\tif  new." + champNameList.get(pos).trim() + "<> " + conditionList.get(pos).trim() + " then\n" +
                "\t\tSIGNAL SQLSTATE '45000'\n" +
                "\t\tSET MESSAGE_TEXT = 'Cannot add or update row: " + champNameList.get(pos).trim() + " for condition: " + conditionList.get(pos).trim() + "';\n" +
                "        end if;";
        return ifRequete;
    }

    /***
     * getConditionTriggerOldString return condition without signal
     * @param champNameList
     * @param conditionList
     * @param pos
     * @return
     */
    private String getConditionTriggerOldString(List<String> champNameList, List<String> conditionList, int pos) {
        String ifRequete = "\t\tif  new."+champNameList.get(pos).trim() + "<> " + conditionList.get(pos).trim() + " then\n" +
                "\t\tSET NEW."+champNameList.get(pos).trim()+" = NULL;\n" +
                "        end if;";
        return ifRequete;
    }


}