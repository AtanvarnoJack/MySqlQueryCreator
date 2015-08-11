package Base.SQLite.SQLJet;

import Base.Cryptage.Crypt;
import Base.Cryptage.CryptMD5.CryptMD5;
import Base.SQLite.BddParams;
import org.tmatesoft.sqljet.core.SqlJetException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by alco on 10/08/2015.
 */
public class BddParamsSQLJet implements BddParams {
    @Override
    public void createBase() throws SqlJetException {
        OpenHelperBddParamsSQLJet openHelper = new OpenHelperBddParamsSQLJet();
        openHelper.createNewBase();
        openHelper.createTables(OpenHelperBddParamsSQLJet.getSqlJetDb());
    }

    @Override
    public String getUserForUrl(String url) throws SqlJetException {
        OpenHelperBddParamsSQLJet openHelper = new OpenHelperBddParamsSQLJet();
        String[] array = openHelper.getRecords(OpenHelperBddParamsSQLJet.getSqlJetDb(), url);
        String user = array[1];
        return user;
    }

    @Override
    public String getCryptPassForUrl(String url) throws SqlJetException {
        OpenHelperBddParamsSQLJet openHelper = new OpenHelperBddParamsSQLJet();
        String[] array = openHelper.getRecords(OpenHelperBddParamsSQLJet.getSqlJetDb(), url);
        String password = array[2];
        return password;
    }

    @Override
    public HashMap<String, List<String>> getAllRecords() throws SqlJetException {
        OpenHelperBddParamsSQLJet openHelper = new OpenHelperBddParamsSQLJet();
        HashMap<String, List<String>> listRecords = openHelper.getAllRecords(OpenHelperBddParamsSQLJet.getSqlJetDb());
        return listRecords;
    }

    @Override
    public void setRecords(String url, String user, String password) throws SqlJetException {
        OpenHelperBddParamsSQLJet openHelper = new OpenHelperBddParamsSQLJet();
        Crypt crypt = new CryptMD5();
        String encodedPassword = crypt.getEncodedPassword(password);
        openHelper.addRecordBase(OpenHelperBddParamsSQLJet.getSqlJetDb(), url, user, encodedPassword);
    }

    @Override
    public void updateRecords(String url, String user, String password) throws SqlJetException {
        OpenHelperBddParamsSQLJet openHelper = new OpenHelperBddParamsSQLJet();
        Crypt crypt = new CryptMD5();
        String encodedPassword = crypt.getEncodedPassword(password);
        openHelper.updateRecords(OpenHelperBddParamsSQLJet.getSqlJetDb(), url, user, encodedPassword);
    }

    @Override
    public void deleteRecords(String url) throws SqlJetException {
        OpenHelperBddParamsSQLJet openHelper = new OpenHelperBddParamsSQLJet();
        openHelper.deleteRecords(OpenHelperBddParamsSQLJet.getSqlJetDb(), url);
    }
}
