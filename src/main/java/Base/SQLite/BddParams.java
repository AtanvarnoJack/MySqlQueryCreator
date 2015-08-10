package Base.SQLite;

/**
 * Created by alco on 10/08/2015.
 */
public interface BddParams {
    boolean createBase();

    String getUserForUrl(String url);

    String getCryptPassForUrl(String url);

    void setConnection(String url, String user, String password);
}
