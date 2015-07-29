import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by alco on 29/07/2015.
 */
public class TestBaseConnection {
    @Test
    public void ConnectionBase() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysqqfdl://localhoyhst:3306/alpha", "root", "");
        } catch (CommunicationsException e) {
            System.out.println("e = " + e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("conn = " + conn);
    }
}
