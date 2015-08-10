import Base.Cryptage.Crypt;
import Base.Cryptage.CryptMD5.CryptMD5;
import org.testng.annotations.Test;

import java.security.NoSuchAlgorithmException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by alco on 10/08/2015.
 */
public class TestCrypt {

    @Test
    public void testCrypt() {
        Crypt crypt = new CryptMD5();

        System.out.println(crypt.getEncodedPassword("mot de passe"));
        try {

            boolean found = crypt.testPassword("mot de passe", "729f2d8b3d3d9bc07ba349faab7fdf44");
            assertThat(found).isTrue();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
