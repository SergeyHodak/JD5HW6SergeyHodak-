package tomcat_starter;

import lombok.Getter;
import prefs.Prefs;
import storage.DatabaseInitService;
import storage.Storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TomcatStarter {
    @Getter
    private static final String connectionUrl = "jdbc:h2:./test";
    @Getter
    private static Connection connection = null;

    static {
        try {
            connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new DatabaseInitService().initDb(connectionUrl);
    }
}