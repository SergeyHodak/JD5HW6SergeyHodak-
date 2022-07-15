import prefs.Prefs;
import storage.DatabaseInitService;
import tables.BasicContentOfTables;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        new DatabaseInitService().initDb(new Prefs().getString(Prefs.DB_JDBC_CONNECTION_URL));

        //TODO Uncomment to populate tables with test data!
//        new BasicContentOfTables().toFill();
    }
}