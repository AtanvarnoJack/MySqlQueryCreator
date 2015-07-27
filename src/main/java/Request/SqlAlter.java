package Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alco on 16/07/2015.
 * Contain all Alter Request
 */
public class SqlAlter {
    private final static String ALTER_TO_INNODB = "AlterToInnoDB";
    private final static String ALTER_TO_MYISAM = "AlterToMyIsam";
    private final static String INNODB = "InnoDB";
    private final static String MYISAM = "MyIsam";

    public static String getAlterToInnodb() {
        return ALTER_TO_INNODB;
    }

    public static String getAlterToMyisam() {
        return ALTER_TO_MYISAM;
    }

    public String getAllAlterTableEngine(List<String> tableNameList, String engine){
        String allRequest = "";
        List<String> tableNameListSort = new ArrayList<String>();

        for (String tableName : tableNameList) {
            boolean exist = tryOccurenceExist(tableNameListSort, tableName);
            if (!exist){
                tableNameListSort.add(tableName);
            }
        }

        for (String name : tableNameListSort) {
            allRequest = allRequest + "\n" + getAlterTableEngine(name, engine);
        }

        return allRequest;
    }

    public String getAlterTableEngine(String tableName, String engine) {
        String request = "";
        if (engine.equals(ALTER_TO_INNODB)) {
            request = "ALTER TABLE `" + tableName + "` ENGINE=" + INNODB + ";";
        } else if (engine.equals(ALTER_TO_MYISAM)) {
            request = "ALTER TABLE `" + tableName + "` ENGINE=" + MYISAM + ";";
        }
        return request;
    }

    private Boolean tryOccurenceExist(List<String> list, String table) {
        Boolean bool = false;

        for (String found : list) {
            if (table.equals(found)) {
                bool = true;
            }
        }

        return bool;
    }
}
