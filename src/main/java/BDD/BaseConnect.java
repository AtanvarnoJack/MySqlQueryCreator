package BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by alco on 29/07/2015.
 */
public class BaseConnect {

    private static Connection conn = null;

    public BaseConnect() {
    }

    public BaseConnect(String url, String user, String password) throws SQLException {
        try {
            conn = DriverManager.getConnection(url, user, password);
            IdConnection.setURL(url);
            IdConnection.setUser(user);
            IdConnection.setPassword(password);
        } catch (SQLException SqlE) {
            throw new SQLException("Can't connect to BDD please check url, user or password!\nSqlE");
        }
    }

    public boolean tryConnection(String url, String user, String password) {
        boolean connection;
        try {
            DriverManager.getConnection(url, user, password);
            connection = true;
        } catch (SQLException SqlE) {
            connection = false;
        }
        return connection;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseConnect that = (BaseConnect) o;

        if (conn != null ? !conn.equals(that.conn) : that.conn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return conn != null ? conn.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "baseConnection{" +
                "conn=" + conn +
                '}';
    }
}
