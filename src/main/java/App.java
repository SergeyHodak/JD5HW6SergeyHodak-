import prefs.Prefs;
import storage.DatabaseInitService;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        Prefs prefs = new Prefs();
        String url = prefs.getString(Prefs.DB_JDBC_CONNECTION_URL);
        String username = prefs.getString(Prefs.DB_JDBC_USERNAME);
        String password = prefs.getString(Prefs.DB_JDBC_USER_PASSWORD);
        new DatabaseInitService().initDb(url, username, password);
    }
}