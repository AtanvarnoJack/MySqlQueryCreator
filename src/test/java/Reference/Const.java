package Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alco on 06/07/2015.
 */
public class Const {

    public Const() {}

    public Const(String tableName, String champName, String condition) {
        this.tableName = tableName;
        this.champName = champName;
        this.condition = condition;
    }

    public List<String> getTableNameList() {
        List<String> tableNameList = new ArrayList<String>();
        String[] arrayFound = tableName.split(";");
        for (String chaine : arrayFound) {
            tableNameList.add(chaine.trim());
        }
        return tableNameList;
    }

    public List<String> getChampNameList() {
        List<String> champNameList = new ArrayList<String>();
        String[] arrayFound = champName.split(";");
        for (String chaine : arrayFound) {
            champNameList.add(chaine.trim());
        }
        return champNameList;
    }

    public List<String> getConditionList() {
        List<String> conditionList = new ArrayList<String>();
        String[] arrayFound = condition.split(";");
        for (String chaine : arrayFound) {
            conditionList.add(chaine.trim());
        }
        return conditionList;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getChampName() {
        return champName;
    }

    public void setChampName(String champName) {
        this.champName = champName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    private String tableName = "CLIENT_RED" +
            ";CLIENT_RED_BKP" +
            ";CLIENT_RED_TMP" +
            ";CLIENT_V3" +
            ";CLIENT_V3" +
            ";CLIENT_V3" +
            ";CLIENT_V3_NEW" +
            ";CLIENT_V3_NEW" +
            ";CLIENT_V3_NEW" +
            ";EQUIPMENTS" +
            ";EQUIPMENTS" +
            ";EQUIPMENTS" +
            ";EQUIPMENTS" +
            ";EQUIPMENTS_BKP" +
            ";EQUIPMENTS_BKP" +
            ";EQUIPMENTS_BKP" +
            ";EQUIPMENTS_BKP" +
            ";EQUIPMENTS_TMP" +
            ";EQUIPMENTS_TMP" +
            ";EQUIPMENTS_TMP" +
            ";EQUIPMENTS_TMP" +
            ";LIAISON_V3" +
            ";LIAISON_V3" +
            ";LIAISON_V3" +
            ";LIAISON_V3" +
            ";LIAISON_V3" +
            ";LIAISON_V3" +
            ";LIAISON_V3" +
            ";LIAISON_V3" +
            ";LIAISON_V3" +
            ";LIAISON_V3" +
            ";LIAISON_V3" +
            ";LIAISON_V3" +
            ";LIAISON_V3" +
            ";LIAISON_V3" +
            ";LIAISON_V3" +
            ";LIAISON_V3" +
            ";LIAISON_V3_BKP" +
            ";LIAISON_V3_BKP" +
            ";LIAISON_V3_BKP" +
            ";LIAISON_V3_BKP" +
            ";LIAISON_V3_BKP" +
            ";LIAISON_V3_BKP" +
            ";LIAISON_V3_BKP" +
            ";LIAISON_V3_BKP" +
            ";LIAISON_V3_BKP" +
            ";LIAISON_V3_BKP" +
            ";LIAISON_V3_BKP" +
            ";LIAISON_V3_BKP" +
            ";LIAISON_V3_BKP" +
            ";LIAISON_V3_BKP" +
            ";LIAISON_V3_BKP" +
            ";LIAISON_V3_BKP" +
            ";LIAISON_V3_NEW" +
            ";LIAISON_V3_NEW" +
            ";LIAISON_V3_NEW" +
            ";LIAISON_V3_NEW" +
            ";LIAISON_V3_NEW" +
            ";LIAISON_V3_NEW" +
            ";LIAISON_V3_NEW" +
            ";LIAISON_V3_NEW" +
            ";LIAISON_V3_NEW" +
            ";LIAISON_V3_NEW" +
            ";LIAISON_V3_NEW" +
            ";LIAISON_V3_NEW" +
            ";LIAISON_V3_NEW" +
            ";LIAISON_V3_NEW" +
            ";LIAISON_V3_NEW" +
            ";LIAISON_V3_NEW" +
            ";SPO_NEW" +
            ";";

    private String champName = "RED_CONTRACT_TYPE" +
            ";RED_CONTRACT_TYPE" +
            ";RED_CONTRACT_TYPE" +
            ";SDM_EMAIL" +
            ";ISC_EMAIL" +
            ";ISC_BACKUP_EMAIL" +
            ";SDM_EMAIL" +
            ";ISC_EMAIL" +
            ";ISC_BACKUP_EMAIL" +
            ";RED_Under_supervision " +
            ";RED_Snmp_version " +
            ";RED_Contract_level " +
            ";RED_Criticality " +
            ";RED_Under_supervision " +
            ";RED_Snmp_version " +
            ";RED_Contract_level " +
            ";RED_Criticality " +
            ";RED_Under_supervision " +
            ";RED_Snmp_version " +
            ";RED_Contract_level " +
            ";RED_Criticality " +
            ";CARRIER_EMAIL" +
            ";LOCAL_CONTACT1_EMAIL" +
            ";LOCAL_CONTACT2_EMAIL" +
            ";LOCAL_CONTACT3_EMAIL" +
            ";ESCALADE_EMAIL1" +
            ";ESCALADE_EMAIL2" +
            ";INCIDENT_EMAIL1" +
            ";INCIDENT_EMAIL2" +
            ";INCIDENT_EMAIL3" +
            ";INCIDENT_EMAIL4" +
            ";INCIDENT_EMAIL5" +
            ";CHANGE_EMAIL1" +
            ";CHANGE_EMAIL2" +
            ";CHANGE_EMAIL3" +
            ";CHANGE_EMAIL4" +
            ";CHANGE_EMAIL5" +
            ";CARRIER_EMAIL" +
            ";LOCAL_CONTACT1_EMAIL" +
            ";LOCAL_CONTACT2_EMAIL" +
            ";LOCAL_CONTACT3_EMAIL" +
            ";ESCALADE_EMAIL1" +
            ";ESCALADE_EMAIL2" +
            ";INCIDENT_EMAIL1" +
            ";INCIDENT_EMAIL2" +
            ";INCIDENT_EMAIL3" +
            ";INCIDENT_EMAIL4" +
            ";INCIDENT_EMAIL5" +
            ";CHANGE_EMAIL1" +
            ";CHANGE_EMAIL2" +
            ";CHANGE_EMAIL3" +
            ";CHANGE_EMAIL4" +
            ";CHANGE_EMAIL5" +
            ";CARRIER_EMAIL" +
            ";LOCAL_CONTACT1_EMAIL" +
            ";LOCAL_CONTACT2_EMAIL" +
            ";LOCAL_CONTACT3_EMAIL" +
            ";ESCALADE_EMAIL1" +
            ";ESCALADE_EMAIL2" +
            ";INCIDENT_EMAIL1" +
            ";INCIDENT_EMAIL2" +
            ";INCIDENT_EMAIL3" +
            ";INCIDENT_EMAIL4" +
            ";INCIDENT_EMAIL5" +
            ";CHANGE_EMAIL1" +
            ";CHANGE_EMAIL2" +
            ";CHANGE_EMAIL3" +
            ";CHANGE_EMAIL4" +
            ";CHANGE_EMAIL5" +
            ";BGP_UNDER_SUPERVISION" +
            ";";

    private String condition = "'BASIC' or 'ADVANCED' or 'EXTENDED'" +
            ";'BASIC' or 'ADVANCED' or 'EXTENDED'" +
            ";'BASIC' or 'ADVANCED' or 'EXTENDED'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'Y' or 'N'" +
            ";'V1' or 'V2' or 'V3'" +
            ";'HLS' or 'ORS' or 'EORS'" +
            ";'Low' or 'Medium' or 'High'" +
            ";'Y' or 'N'" +
            ";'V1' or 'V2' or 'V3'" +
            ";'HLS' or 'ORS' or 'EORS'" +
            ";'Low' or 'Medium' or 'High'" +
            ";'Y' or 'N'" +
            ";'V1' or 'V2' or 'V3'" +
            ";'HLS' or 'ORS' or 'EORS'" +
            ";'Low' or 'Medium' or 'High'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'%@%'" +
            ";'Y' or 'N'" +
            ";";
}
