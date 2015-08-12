package BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by alco on 29/07/2015.
 */
public class baseConnection {

    Connection conn = null;

    public void testConnection(String url, String user, String password) throws SQLException {
        try {
            conn = DriverManager.getConnection(url + "user=" + user + "&password=" + password);
        } catch (SQLException e) {
            throw new SQLException("Can't connect to BDD please check url, user or password!");
        }
    }

}
