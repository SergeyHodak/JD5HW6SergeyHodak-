package mvc.model;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import prefs.Prefs;
import tables.company.Company;
import tables.company.CompanyDaoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class CompanyCreatePost implements Command {
    private Connection connection;

    public CompanyCreatePost() {
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/jd5hw6";
            String username = "root";
            String password = "12345";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println("Class CompanyCreatePost: " + e.getMessage());
            connection = null;
        }
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String setName = req.getParameter("setName");
        String setDescription = req.getParameter("setDescription");

        Company company = new Company();
        company.setName(setName);
        company.setDescription(setDescription);

        String id = "";
        String error = "";

        try {
            CompanyDaoService companyDaoService = new CompanyDaoService(connection);
            id = String.valueOf(companyDaoService.create(company));
        } catch (Exception e) {
            error = e.toString();
        }

        resp.setContentType("text/html");

        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("companyId", id, "errorMessage", error)
        );

        engine.process("company-create", simpleContext, resp.getWriter());
        resp.getWriter().close();

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}