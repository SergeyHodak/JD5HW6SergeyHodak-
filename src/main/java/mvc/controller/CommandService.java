package mvc.controller;

import mvc.model.*;
import mvc.model.company.*;
import mvc.model.customer.*;
import mvc.model.project.*;
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
        commands.put("GET /app/table/company/get-all", new CompanyGetAll(connection));
        commands.put("POST /app/table/company/get-all", new CompanyGetAll(connection));
        commands.put("GET /app/table/company/update", new CompanyUpdate(connection));
        commands.put("POST /app/table/company/update", new CompanyUpdate(connection));
        commands.put("GET /app/table/company/delete-by-id", new CompanyDeleteById(connection));
        commands.put("POST /app/table/company/delete-by-id", new CompanyDeleteById(connection));

        commands.put("GET /app/table/customer", new CustomerPage());
        commands.put("GET /app/table/customer/create", new CustomerCreate(connection));
        commands.put("POST /app/table/customer/create", new CustomerCreate(connection));
        commands.put("GET /app/table/customer/delete-by-id", new CustomerDeleteById(connection));
        commands.put("POST /app/table/customer/delete-by-id", new CustomerDeleteById(connection));
        commands.put("GET /app/table/customer/get-all", new CustomerGetAll(connection));
        commands.put("POST /app/table/customer/get-all", new CustomerGetAll(connection));
        commands.put("GET /app/table/customer/get-by-id", new CustomerGetById(connection));
        commands.put("POST /app/table/customer/get-by-id", new CustomerGetById(connection));
        commands.put("GET /app/table/customer/update", new CustomerUpdate(connection));
        commands.put("POST /app/table/customer/update", new CustomerUpdate(connection));

        commands.put("GET /app/table/project", new ProjectPage());
        commands.put("GET /app/table/project/create", new ProjectCreate(connection));
        commands.put("POST /app/table/project/create", new ProjectCreate(connection));
        commands.put("GET /app/table/project/delete-by-id", new ProjectDeleteById(connection));
        commands.put("POST /app/table/project/delete-by-id", new ProjectDeleteById(connection));
        commands.put("GET /app/table/project/get-all", new ProjectGetAll(connection));
        commands.put("POST /app/table/project/get-all", new ProjectGetAll(connection));
        commands.put("GET /app/table/project/get-by-id", new ProjectGetById(connection));
        commands.put("POST /app/table/project/get-by-id", new ProjectGetById(connection));
        commands.put("GET /app/table/project/update", new ProjectUpdate(connection));
        commands.put("POST /app/table/project/update", new ProjectUpdate(connection));
        commands.put("GET /app/table/project/get-cost-by-id", new ProjectGetCostById(connection));
        commands.put("POST /app/table/project/get-cost-by-id", new ProjectGetCostById(connection));
        commands.put("GET /app/table/project/update-cost-by-id", new ProjectUpdateCostById(connection));
        commands.put("POST /app/table/project/update-cost-by-id", new ProjectUpdateCostById(connection));
    }

    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String requestUri = req.getRequestURI();
        String commandKey = req.getMethod() + " " + requestUri;

        commands.get(commandKey).process(req, resp, engine);
    }
}