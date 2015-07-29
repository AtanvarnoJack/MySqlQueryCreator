import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by alco on 29/07/2015.
 */
public class TestBaseConnection {

    @Test
    public void ConnectionBase() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/alpha", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertThat(conn).isNotNull();
    }
}
