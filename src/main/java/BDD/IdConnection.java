package BDD;

/**
 * Created by alco on 29/07/2015.
 */
public class IdConnection {
    private static String URL;
    private static String User;
    private static String Password;

    public IdConnection() {
    }

    public IdConnection(String URL, String user, String password) {
        this.URL = URL;
        User = user;
        Password = password;
    }

    public static String getURL() {
        return URL;
    }

    public static void setURL(String URL) {
        IdConnection.URL = URL;
    }

    public static String getUser() {
        return User;
    }

    public static void setUser(String user) {
        User = user;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setPassword(String password) {
        Password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdConnection that = (IdConnection) o;

        if (Password != null ? !Password.equals(that.Password) : that.Password != null) return false;
        if (URL != null ? !URL.equals(that.URL) : that.URL != null) return false;
        if (User != null ? !User.equals(that.User) : that.User != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = URL != null ? URL.hashCode() : 0;
        result = 31 * result + (User != null ? User.hashCode() : 0);
        result = 31 * result + (Password != null ? Password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IdConnection{" +
                "URL='" + URL + '\'' +
                ", User='" + User + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
