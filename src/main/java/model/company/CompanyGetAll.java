package model.company;

import model.Command;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CompanyGetAll implements Command {
    private final Connection connection;

    public CompanyGetAll(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        if (!Objects.equals(req.getMethod(), "POST")) {
            resp.setContentType("text/html");

            Context simpleContext = new Context(
                    req.getLocale(),
                    Map.of("companies", "null", "errorMessage", "")
            );
            engine.process("company/company-get-all", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }
        List<Company> companies = new ArrayList<>();
        String error = "";

        try {
            CompanyDaoService companyDaoService = new CompanyDaoService(connection);
            companies = companyDaoService.getAll();
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("companies", companies.size() == 0 ? "null" : companies,"errorMessage", error)
        );
        engine.process("company/company-get-all", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}