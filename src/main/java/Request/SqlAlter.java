package Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alco on 16/07/2015.
 * Contain all Alter Request
 */
public class SqlAlter {
    private final static String ALTER = "ALTER";
    private final static String ENGINE = "ENGINE";
    private final static String INNODB = "INNODB";
    private final static String MYISAM = "MYISAM";

    public static String getAlter() {
        return ALTER;
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
