package Request.MySQL;

import Request.MainRequest;
import Request.MySQL.Query.SqlAlter;
import Request.MySQL.Query.SqlTrigger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alco on 28/07/2015.
 */
public class MySqlRequest implements MainRequest {
    private double mySqlVersion;

    public MySqlRequest(double mySqlVersion) {
        this.mySqlVersion = mySqlVersion;
    }

    /**
     * createAllRequest sort all list an generate all request in a List<String>.
     *
     * @param typeListSort
     * @param typeList
     * @param tableList
     * @param champsList
     * @param conditionList
     * @return
     */
    @Override
    public List<String> createAllRequest(List<String> typeListSort, List<String> typeList, List<String> tableList, List<String> champsList, List<String> conditionList)
            throws IllegalArgumentException {
        java.util.List<String> tableListPrepare = new ArrayList<String>();
        java.util.List<String> champsListPrepare = new ArrayList<String>();
        java.util.List<String> conditionListPrepare = new ArrayList<String>();

        java.util.List<String> allRequestList = new ArrayList<String>();

        for (String type : typeListSort) {
            tableListPrepare.clear();
            champsListPrepare.clear();
            conditionListPrepare.clear();
            for (int i = 0; i < typeList.size(); i++) {
                if (type.equals(typeList.get(i))) {
                    tableListPrepare.add(tableList.get(i));
                    champsListPrepare.add(champsList.get(i));
                    conditionListPrepare.add(conditionList.get(i));
                }
            }
            detectionType(tableListPrepare, champsListPrepare, conditionListPrepare, allRequestList, type);
        }
        return allRequestList;
    }

    /**
     * detectionType cast all type in different name and calling associated Request Creator
     *
     * @param tableListPrepare
     * @param champsListPrepare
     * @param conditionListPrepare
     * @param allRequestList
     * @param type
     */
    private void detectionType(java.util.List<String> tableListPrepare, java.util.List<String> champsListPrepare, java.util.List<String> conditionListPrepare, java.util.List<String> allRequestList, String type)
            throws IllegalArgumentException {
        String[] architectureType = type.trim().toUpperCase().split(":");

        if (architectureType.length != 0) {
            if (architectureType[0].equals(SqlTrigger.getTrigger())) {
                parseTrigger(tableListPrepare, champsListPrepare, conditionListPrepare, allRequestList);
            } else if (architectureType[0].equals(SqlAlter.getAlter())) {
                parseAlter(tableListPrepare, champsListPrepare, conditionListPrepare, allRequestList, architectureType);
            } else {
                throw new IllegalArgumentException("Architecture's type is not correct!\n" +
                        "Rows: " + Arrays.toString(architectureType));
            }
        } else {
            throw new IllegalArgumentException("Architecture's type is not correct!\n" +
                    "Type: " + type);
        }
    }

    /**
     * parseTrigger call all method for Trigger Query
     *
     * @param tableListPrepare
     * @param champsListPrepare
     * @param conditionListPrepare
     * @param allRequestList
     */
    private void parseTrigger(List<String> tableListPrepare, List<String> champsListPrepare, List<String> conditionListPrepare, List<String> allRequestList) {
        SqlTrigger sqlTrigger = new SqlTrigger();
        allRequestList.add(sqlTrigger.createListSqlDropIfExist(tableListPrepare));
        allRequestList.add(sqlTrigger.createSqlTriggerList(tableListPrepare, champsListPrepare, conditionListPrepare, mySqlVersion));
    }

    /**
     * parseAlter call all method for Alter query
     *
     * @param tableListPrepare
     * @param champsListPrepare
     * @param conditionListPrepare
     * @param allRequestList
     * @param architectureType
     */
    private void parseAlter(java.util.List<String> tableListPrepare, java.util.List<String> champsListPrepare, java.util.List<String> conditionListPrepare, java.util.List<String> allRequestList, String[] architectureType) {
        if (architectureType.length >= 2) {
            if (architectureType[1].equals(SqlAlter.getEngine())) {
                parseEngine(tableListPrepare, allRequestList, architectureType);
            } else if (architectureType[1].equals(SqlAlter.getConstraint())) {
                parseConstraint(tableListPrepare, champsListPrepare, conditionListPrepare, allRequestList);
            } else {
                throw new IllegalArgumentException("Architecture's type is not correct!\n" +
                        "Alter Type: " + architectureType[1] + " for " + Arrays.toString(architectureType));
            }
        } else {
            throw new IllegalArgumentException("Architecture's type is not correct!\n" +
                    "Type: " + architectureType[0] + " for " + Arrays.toString(architectureType));
        }
    }

    /**
     * parseConstraint call all method constraint
     *
     * @param tableListPrepare
     * @param champsListPrepare
     * @param conditionListPrepare
     * @param allRequestList
     */
    private void parseConstraint(List<String> tableListPrepare, List<String> champsListPrepare, List<String> conditionListPrepare, List<String> allRequestList) {
        SqlAlter sqlAlter = new SqlAlter();
        allRequestList.add(sqlAlter.getAllAlterTableConstraint(tableListPrepare, champsListPrepare, conditionListPrepare));
    }

    /**
     * parseEngine call all method engine
     *
     * @param tableListPrepare
     * @param allRequestList
     * @param architectureType
     */
    private void parseEngine(List<String> tableListPrepare, List<String> allRequestList, String[] architectureType) {
        if (architectureType[2].equals(SqlAlter.getInnodb())) {
            SqlAlter sqlAlter = new SqlAlter();
            allRequestList.add(sqlAlter.getAllAlterTableEngine(tableListPrepare, SqlAlter.getInnodb()));
        } else if (architectureType[2].equals(SqlAlter.getMyisam())) {
            SqlAlter sqlAlter = new SqlAlter();
            allRequestList.add(sqlAlter.getAllAlterTableEngine(tableListPrepare, SqlAlter.getMyisam()));
        } else {
            throw new IllegalArgumentException("Architecture's type is not correct!\n" +
                    " Engine Name:" + architectureType[2] + " for " + Arrays.toString(architectureType));
        }
    }

    public double getMySqlVersion() {
        return mySqlVersion;
    }

    public void setMySqlVersion(double mySqlVersion) {
        this.mySqlVersion = mySqlVersion;
    }
}
