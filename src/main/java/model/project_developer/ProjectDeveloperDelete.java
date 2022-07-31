package model.project_developer;

import model.Command;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import java.util.Objects;

public class ProjectDeveloperDelete implements Command {
    private final Connection connection;

    public ProjectDeveloperDelete(Connection connection) {
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
            engine.process("project_developer/project-developer-delete", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }

        String setProjectId = req.getParameter("setProjectId");
        String setDeveloperId = req.getParameter("setDeveloperId");

        String error = "";

        try {
            ProjectDeveloper projectDeveloper = new ProjectDeveloper();
            projectDeveloper.setProjectId(Long.parseLong(setProjectId));
            projectDeveloper.setDeveloperId(Long.parseLong(setDeveloperId));

            ProjectDeveloperDaoService projectDeveloperDaoService = new ProjectDeveloperDaoService(connection);
            error = projectDeveloperDaoService.delete(projectDeveloper);
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("errorMessage", error)
        );

        engine.process("project_developer/project-developer-delete", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}