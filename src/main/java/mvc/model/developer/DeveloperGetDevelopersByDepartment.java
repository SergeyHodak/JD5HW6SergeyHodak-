package mvc.model.developer;

import mvc.model.Command;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DeveloperGetDevelopersByDepartment implements Command {
    private final Connection connection;

    public DeveloperGetDevelopersByDepartment(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        if (!Objects.equals(req.getMethod(), "POST")) {
            resp.setContentType("text/html");

            Context simpleContext = new Context(
                    req.getLocale(),
                    Map.of("developers", "null", "errorMessage", "")
            );
            engine.process("developer\\developer-get-developers-by-department", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }
        String setDepartment = req.getParameter("setDepartment");
        List<Developer> developers = null;
        String error = "";

        try {
            DeveloperDaoService developerDaoService = new DeveloperDaoService(connection);
            developers = developerDaoService.getDevelopersByDepartment(setDepartment);
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("developers", developers == null ? "null" : developers, "errorMessage", error)
        );
        engine.process("developer\\developer-get-developers-by-department", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}