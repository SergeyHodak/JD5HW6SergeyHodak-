package mvc.controller;

import mvc.model.*;
import org.thymeleaf.TemplateEngine;
import prefs.Prefs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class CommandService {
    private Connection connection;
    private final Map<String, Command> commands;

    public CommandService() {
        try {
            String url = Prefs.DB_URL;
            String username = Prefs.DB_USERNAME;
            String password = Prefs.DB_PASSWORD;
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        commands = new HashMap<>();

        commands.put("GET /app/table", new TableSelection());
        commands.put("GET /app/table/company", new CompanyPage());
        commands.put("GET /app/table/company/create", new CompanyCreate(connection));
        commands.put("POST /app/table/company/create", new CompanyCreate(connection));
        commands.put("GET /app/table/company/get-by-id", new CompanyGetById(connection));
        commands.put("POST /app/table/company/get-by-id", new CompanyGetById(connection));
    }

    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String requestUri = req.getRequestURI();
        String commandKey = req.getMethod() + " " + requestUri;

        commands.get(commandKey).process(req, resp, engine);
    }
}