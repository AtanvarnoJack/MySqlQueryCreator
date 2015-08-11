package Base.Cryptage;

import java.security.NoSuchAlgorithmException;

/**
 * Created by alco on 10/08/2015.
 */
public interface Crypt {
    String getEncodedPassword(String key);

    boolean testPassword(String clearTextTestPassword,
                         String encodedActualPassword)
            throws NoSuchAlgorithmException;
}
