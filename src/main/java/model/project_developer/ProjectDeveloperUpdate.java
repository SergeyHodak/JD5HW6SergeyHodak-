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

public class ProjectDeveloperUpdate implements Command {
    private final Connection connection;

    public ProjectDeveloperUpdate(Connection connection) {
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
            engine.process("project_developer\\project-developer-update", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }

        String setOldProjectId = req.getParameter("setOldProjectId");
        String setOldDeveloperId = req.getParameter("setOldDeveloperId");
        String setNewProjectId = req.getParameter("setNewProjectId");
        String setNewDeveloperId = req.getParameter("setNewDeveloperId");

        String error = "";

        try {
            ProjectDeveloper newProjectDeveloper = new ProjectDeveloper();
            newProjectDeveloper.setProjectId(Long.parseLong(setNewProjectId));
            newProjectDeveloper.setDeveloperId(Long.parseLong(setNewDeveloperId));

            ProjectDeveloperDaoService projectDeveloperDaoService = new ProjectDeveloperDaoService(connection);
            error = projectDeveloperDaoService.update(
                    Long.parseLong(setOldProjectId),
                    Long.parseLong(setOldDeveloperId),
                    newProjectDeveloper
            );
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("errorMessage", error)
        );

        engine.process("project_developer\\project-developer-update", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}