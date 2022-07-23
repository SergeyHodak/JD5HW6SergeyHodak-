import prefs.Prefs;
import storage.DatabaseInitService;

public class App {
    public static void main(String[] args) {
        String url = Prefs.DB_URL;
        String username = Prefs.DB_USERNAME;
        String password = Prefs.DB_PASSWORD;
        new DatabaseInitService().initDb(url, username, password);
    }
}