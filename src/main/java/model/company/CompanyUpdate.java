package model.company;

import model.Command;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import java.util.Objects;

public class CompanyUpdate implements Command {
    private final Connection connection;

    public CompanyUpdate(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        if (!Objects.equals(req.getMethod(), "POST")) {
            resp.setContentType("text/html");

            Context simpleContext = new Context(
                    req.getLocale(),
                    Map.of("errorMessage", "")
            );
            engine.process("company\\company-update", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }
        String setId = req.getParameter("setId");
        String setName = req.getParameter("setName");
        String setDescription = req.getParameter("setDescription");

        String error = "";

        try {
            Company company = new Company();
            company.setId(Long.parseLong(setId));
            company.setName(setName);
            company.setDescription(setDescription);
            CompanyDaoService companyDaoService = new CompanyDaoService(connection);
            error = companyDaoService.update(company);
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("errorMessage", error)
        );

        engine.process("company\\company-update", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}