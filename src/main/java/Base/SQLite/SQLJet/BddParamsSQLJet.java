package Base.SQLite.SQLJet;

import Base.SQLite.BddParams;

/**
 * Created by alco on 10/08/2015.
 */
public class BddParamsSQLJet implements BddParams {
    @Override
    public boolean createBase() {
        boolean baseCreated = false;
        
        return baseCreated;
    }

    @Override
    public String getUserForUrl(String url) {
        String user = null;
        return user;
    }

    @Override
    public String getCryptPassForUrl(String url) {
        String user = null;
        return user;
    }

    @Override
    public void setConnection(String url, String user, String password) {
        OpenHelperBddParamsSQLJet openHelper = new OpenHelperBddParamsSQLJet();
    }
}
