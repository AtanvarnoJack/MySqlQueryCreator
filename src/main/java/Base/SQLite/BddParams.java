package Base.SQLite;

import org.tmatesoft.sqljet.core.SqlJetException;

/**
 * Created by alco on 10/08/2015.
 */
public interface BddParams {
    void createBase() throws SqlJetException;

    String getUserForUrl(String url) throws SqlJetException;

    String getCryptPassForUrl(String url) throws SqlJetException;

    void setConnection(String url, String user, String password) throws SqlJetException;
}
