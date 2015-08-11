import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by alco on 29/07/2015.
 */
public class TestBaseConnection {
    @Test
    public void ConnectionBase() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/alpha", "root", "");
        assertThat(conn).isNotNull();
    }


}
