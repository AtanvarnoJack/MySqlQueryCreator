package Base.SQLite;

import org.tmatesoft.sqljet.core.SqlJetException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by alco on 10/08/2015.
 */
public interface BddParams {
    void createBase() throws SqlJetException;

    String getUserForUrl(String url) throws SqlJetException;

    String getCryptPassForUrl(String url) throws SqlJetException;

    HashMap<String, List<String>> getAllRecords() throws SqlJetException;

    void setRecords(String url, String user, String password) throws SqlJetException;

    void updateRecords(String url, String user, String password) throws SqlJetException;

    void deleteRecords(String url) throws SqlJetException;
}
