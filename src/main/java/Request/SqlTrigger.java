package Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alco on 03/07/2015.
 */
public class SqlTrigger {

    public static final double REQUEST_NUMBER_VERSION_CHANGE = 5.1;

    public String toStringGetAll(List<String> tableNameList, List<String> champNameList, List<String> conditionList,  double currentVersion) {
        return getAllDropIfExist(tableNameList) + "\n" + createSqlTriggerList(tableNameList, champNameList, conditionList, currentVersion);
    }

    public String getAllDropIfExist(List<String> tableNameList) {
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

    protected String getDropIfExist(String triggerName) {
        String chaine =
                "drop trigger if exists " + triggerName + "_UPDATE;\n" +
                        "drop trigger if exists " + triggerName + "_INSERT;";
        return chaine;
    }

    private String getOldRequestTrigger(List<String> tableNameList, List<String> champNameList, List<String> conditionList, String mySqlChaine, List<String> tableGenerate, int i) {
        String tableName = tableNameList.get(i);
        String triggerRequete = "";
        int j = i;

        Boolean generate = tryOccurenceNotExist(tableGenerate, tableName);

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
            mySqlChaine = mySqlChaine + "\n" + getString(tableNameList, i, triggerRequete);
        }
        return mySqlChaine;
    }

    private String getSignalTriggerRequest(List<String> tableNameList, List<String> champNameList, List<String> conditionList, String mySqlChaine, List<String> tableGenerate, int i) {
        String tableName = tableNameList.get(i);
        String triggerRequete = "";
        int j = i;

        Boolean generate = tryOccurenceNotExist(tableGenerate, tableName);

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
            mySqlChaine = mySqlChaine + "\n" + getString(tableNameList, i, triggerRequete);
        }
        return mySqlChaine;
    }

    private String getString(List<String> tableNameList, int pos, String triggerRequete) {
        String mySqlChaine =
                "delimiter $$\n" +
                "create trigger " + tableNameList.get(pos).trim() + "_UPDATE BEFORE UPDATE on " + tableNameList.get(pos).trim() + "\n" +
                "\tfor each row\n" +
                "\t\tbegin\n" +
                triggerRequete + "\n" +
                "end;\n" +
                "$$\n" +
                "\n" +
                "delimiter $$\n" +
                "create trigger " + tableNameList.get(pos).trim() + "_INSERT BEFORE INSERT on " + tableNameList.get(pos).trim() + "\n" +
                "\tfor each row\n" +
                "\t\tbegin\n" +
                triggerRequete + "\n" +
                "end;\n" +
                "$$";
        return mySqlChaine;
    }

    private String getConditionTriggerString(List<String> champNameList, List<String> conditionList, int pos) {
        String ifRequete = "\t\tif  new." + champNameList.get(pos).trim() + "<> " + conditionList.get(pos).trim() + " then\n" +
                "\t\tSIGNAL SQLSTATE '45000'\n" +
                "\t\tSET MESSAGE_TEXT = 'Cannot add or update row: " + champNameList.get(pos).trim() + " for condition: " + conditionList.get(pos).trim() + "';\n" +
                "        end if;";
        return ifRequete;
    }

    private String getConditionTriggerOldString(List<String> champNameList, List<String> conditionList, int pos) {
        String ifRequete = "\t\tif  new."+champNameList.get(pos).trim() + "<> " + conditionList.get(pos).trim() + " then\n" +
                "\t\tSET NEW."+champNameList.get(pos).trim()+" = NULL;\n" +
                "        end if;";
        return ifRequete;
    }

    private Boolean tryOccurenceNotExist(List<String> list, String table) {
        Boolean bool = true;

        for (String found : list) {
            if (table.equals(found)) {
                bool = false;
            }
        }

        return bool;
    }

}