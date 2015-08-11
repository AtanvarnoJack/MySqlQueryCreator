import Base.Cryptage.Crypt;
import Base.Cryptage.CryptMD5.CryptMD5;
import Base.SQLite.BddParams;
import Base.SQLite.SQLJet.BddParamsSQLJet;
import org.testng.annotations.Test;
import org.tmatesoft.sqljet.core.SqlJetException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by alco on 11/08/2015.
 */
public class TestSQLJetParams {
    private String url = "jdbc:mysqqfdl://localhoyhst:3306/alpha";
    private String user = "root";
    private String password = "Pa33w0Rd";

    @Test
    public void createConnectionToBase() throws SqlJetException {
        BddParams bddParams = new BddParamsSQLJet();
        bddParams.createBase();
        bddParams.setRecords(url, user, password);
    }


    @Test
    public void canFoundUser() throws SqlJetException {
        BddParams bddParams = new BddParamsSQLJet();
        String userFound = bddParams.getUserForUrl(url);
        assertThat(userFound).isEqualTo(user);
    }

    @Test
    public void canFoundPass() throws SqlJetException {
        BddParams bddParams = new BddParamsSQLJet();
        Crypt crypt = new CryptMD5();
        String encodedPass = crypt.getEncodedPassword(password);
        String passwordFound = bddParams.getCryptPassForUrl(url);
        assertThat(passwordFound).isEqualTo(encodedPass);
    }

    @Test
    public void canFoundAll() throws SqlJetException {
        BddParams bddParams = new BddParamsSQLJet();
        bddParams.createBase();
        bddParams.setRecords(url, user, password);
        bddParams.setRecords("test", "BRUCE", "WAYNE");
        HashMap<String, List<String>> recordsListFound = bddParams.getAllRecords();
        for (Map.Entry<String, List<String>> entry : recordsListFound.entrySet()) {
            assertThat(entry.getKey()).isNotNull();
            assertThat(entry.getValue()).isNotNull();
            assertThat(entry.getValue().size()).isEqualTo(2);
        }
        assertThat(recordsListFound.size()).isEqualTo(2);
    }

    @Test
    public void canUpdateRecords() throws SqlJetException {
        BddParams bddParams = new BddParamsSQLJet();
        String newUser = "Bruce";
        bddParams.updateRecords(url, newUser, password);
        String userFound = bddParams.getUserForUrl(url);
        assertThat(userFound).isEqualTo(newUser);
    }

    @Test
    public void canDeleteRecords() throws SqlJetException {
        BddParams bddParams = new BddParamsSQLJet();
        bddParams.deleteRecords("test");
        HashMap<String, List<String>> listRecordsFound = bddParams.getAllRecords();
        assertThat(listRecordsFound.size()).isEqualTo(1);
        bddParams.deleteRecords(url);
        HashMap<String, List<String>> listRecordsFound2 = bddParams.getAllRecords();
        assertThat(listRecordsFound2.size()).isEqualTo(0);
    }
}
