package mvc.model.project;

import mvc.model.Command;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tables.project.ProjectDaoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import java.util.Objects;

public class ProjectUpdateCostById implements Command {
    private final Connection connection;

    public ProjectUpdateCostById(Connection connection) {
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
            engine.process("project\\project-update-cost-by-id", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }
        String setId = req.getParameter("setId");

        String error = "";

        try {
            ProjectDaoService projectDaoService = new ProjectDaoService(connection);
            error = projectDaoService.updateCostById(Long.parseLong(setId));
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("errorMessage", error)
        );

        engine.process("project\\project-update-cost-by-id", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}