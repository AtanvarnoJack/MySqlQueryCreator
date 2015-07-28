package Request.MySQL.Query;

import Request.Analytics.Analytics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alco on 16/07/2015.
 * Contain all Alter Request
 */
public class SqlAlter {
    private final static String ALTER = "ALTER";
    private final static String CONSTRAINT = "CONSTRAINT";
    private final static String ENGINE = "ENGINE";
    private final static String INNODB = "INNODB";
    private final static String MYISAM = "MYISAM";

    public static String getAlter() {
        return ALTER;
    }

    public static String getConstraint() {
        return CONSTRAINT;
    }

    public static String getEngine() {
        return ENGINE;
    }

    public static String getInnodb() {
        return INNODB;
    }

    public static String getMyisam() {
        return MYISAM;
    }

    public String getAllAlterTableConstraint(List<String> tableNameList, List<String> fieldNameList, List<String> keyList) {
        String allRequest = "";

        List<String> tableNameListSort = new ArrayList<String>();
        List<String> fieldNameListSort = new ArrayList<String>();
        List<String> keyNameListSort = new ArrayList<String>();

        for (int i = 0; i < tableNameList.size(); i++) {
            Analytics analytics = new Analytics();
            boolean exist = analytics.tryOccurrenceExist(tableNameListSort, tableNameList.get(i)) && analytics.tryOccurrenceExist(fieldNameListSort, fieldNameList.get(i));
            if (!exist) {
                tableNameListSort.add(tableNameList.get(i));
                fieldNameListSort.add(fieldNameList.get(i));
                keyNameListSort.add(keyList.get(i));
            }
        }

        for (int j = 0; j < tableNameListSort.size(); j++) {
            allRequest = allRequest + "\n" + getAlterTableConstraint(tableNameListSort.get(j), fieldNameListSort.get(j), keyNameListSort.get(j));
        }

        return allRequest;
    }

    /**
     * @param tableName
     * @param fieldName
     * @param Key
     * @return
     */
    private String getAlterTableConstraint(String tableName, String fieldName, String Key) {
        return "ALTER TABLE " + fieldName + " ADD CONSTRAINT " + tableName + " PRIMARY KEY(" + Key + ");";
    }

    /**
     * getAllAlterTableEngine call and concat all request
     *
     * @param tableNameList
     * @param engine
     * @return
     */
    public String getAllAlterTableEngine(List<String> tableNameList, String engine){
        String allRequest = "";
        List<String> tableNameListSort = new ArrayList<String>();

        for (String tableName : tableNameList) {
            Analytics analytics = new Analytics();
            boolean exist = analytics.tryOccurrenceExist(tableNameListSort, tableName);
            if (!exist){
                tableNameListSort.add(tableName);
            }
        }

        for (String name : tableNameListSort) {
            allRequest = allRequest + "\n" + getAlterTableEngine(name, engine);
        }

        return allRequest;
    }

    /***
     * getAlterTableEngine return a request for one engine
     * @param tableName
     * @param engine
     * @return
     */
    public String getAlterTableEngine(String tableName, String engine) {
        String request = "";
        if (engine.equals(INNODB)) {
            request = "ALTER TABLE `" + tableName + "` ENGINE=" + INNODB + ";";
        } else if (engine.equals(MYISAM)) {
            request = "ALTER TABLE `" + tableName + "` ENGINE=" + MYISAM + ";";
        }
        return request;
    }
}
