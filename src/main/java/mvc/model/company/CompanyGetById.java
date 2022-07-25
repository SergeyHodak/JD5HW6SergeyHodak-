package mvc.model.company;

import mvc.model.Command;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.company.Company;
import tables.company.CompanyDaoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import java.util.Objects;

public class CompanyGetById implements Command {
    private final Connection connection;

    public CompanyGetById(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        if (!Objects.equals(req.getMethod(), "POST")) {
            resp.setContentType("text/html");

            Context simpleContext = new Context(
                    req.getLocale(),
                    Map.of("companyId", "",
                            "companyName", "",
                            "companyDescription", "",
                            "errorMessage", "")
            );
            engine.process("company\\company-get-by-id", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }
        String id = req.getParameter("setId");
        Company company = new Company();
        String error = "";

        try {
            CompanyDaoService companyDaoService = new CompanyDaoService(connection);
            company = companyDaoService.getById(Long.parseLong(id));
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("companyId", company.getId()  == 0 ? "null" : company.getId(),
                        "companyName", company.getName() == null ? "null" : company.getName(),
                        "companyDescription", company.getDescription() == null ? "null" : company.getDescription(),
                        "errorMessage", error)
        );
        engine.process("company\\company-get-by-id", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}