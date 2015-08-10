package Base.SQLite.SQLJet;

import Base.Cryptage.Crypt;
import Base.Cryptage.CryptMD5.CryptMD5;
import Base.SQLite.BddParams;
import org.tmatesoft.sqljet.core.SqlJetException;

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
        String user = openHelper.getRecords(OpenHelperBddParamsSQLJet.getSqlJetDb(), url);
        return user;
    }

    @Override
    public String getCryptPassForUrl(String url) throws SqlJetException {
        OpenHelperBddParamsSQLJet openHelper = new OpenHelperBddParamsSQLJet();
        String password = openHelper.getRecords(OpenHelperBddParamsSQLJet.getSqlJetDb(), url);
        return password;
    }

    @Override
    public void setConnection(String url, String user, String password) throws SqlJetException {
        OpenHelperBddParamsSQLJet openHelper = new OpenHelperBddParamsSQLJet();
        Crypt crypt = new CryptMD5();
        String encodedPassword = crypt.getEncodedPassword(password);
        openHelper.addRecordBase(OpenHelperBddParamsSQLJet.getSqlJetDb(), url, user, encodedPassword);
    }
}
