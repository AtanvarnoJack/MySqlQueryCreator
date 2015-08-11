package Base.Cryptage.CryptMD5;

import Base.Cryptage.Crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by alco on 10/08/2015.
 */
public class CryptMD5 implements Crypt {
    @Override
    public String getEncodedPassword(String key) {
        byte[] uniqueKey = key.getBytes();
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        } catch (NoSuchAlgorithmException e) {
            throw new Error("no MD5 support in this VM");
        }
        StringBuffer hashString = new StringBuffer();
        for (int i = 0; i < hash.length; ++i) {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1) {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            } else {
                hashString.append(hex.substring(hex.length() - 2));
            }
        }
        return hashString.toString();
    }

    @Override
    public boolean testPassword(String clearTextTestPassword,
                                String encodedActualPassword)
            throws NoSuchAlgorithmException {
        String encodedTestPassword = getEncodedPassword(
                clearTextTestPassword);

        return (encodedTestPassword.equals(encodedActualPassword));
    }
}
