import Base.Cryptage.Crypt;
import Base.Cryptage.Md5.CryptMd5;
import org.testng.annotations.Test;

import java.security.NoSuchAlgorithmException;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by alco on 10/08/2015.
 */
public class TestCrypt {

    public static boolean testPassword(String clearTextTestPassword,
                                       String encodedActualPassword)
            throws NoSuchAlgorithmException {
        Crypt crypt = new CryptMd5();
        String encodedTestPassword = crypt.getEncodedPassword(
                clearTextTestPassword);

        return (encodedTestPassword.equals(encodedActualPassword));
    }

    @Test
    public void testCrypt() {
        Crypt crypt = new CryptMd5();

        System.out.println(crypt.getEncodedPassword("mot de passe"));
        try {

            boolean found = testPassword("mot de passe", "729f2d8b3d3d9bc07ba349faab7fdf44");
            assertThat(found).isTrue();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
