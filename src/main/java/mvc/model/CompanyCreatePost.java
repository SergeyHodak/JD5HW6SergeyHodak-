package mvc.model;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import storage.Storage;
import tables.company.Company;
import tables.company.CompanyDaoService;
import tomcat_starter.TomcatStarter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

public class CompanyCreatePost implements Command {
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
            Connection connection = TomcatStarter.getConnection();
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
    }
}