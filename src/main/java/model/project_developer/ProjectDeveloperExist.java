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

public class ProjectDeveloperExist implements Command {
    private final Connection connection;

    public ProjectDeveloperExist(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        if (!Objects.equals(req.getMethod(), "POST")) {
            resp.setContentType("text/html");

            Context simpleContext = new Context(
                    req.getLocale(),
                    Map.of("result", "", "errorMessage", "")
            );
            engine.process("project_developer/project-developer-exit", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }

        String setProjectId = req.getParameter("setProjectId");
        String setDeveloperId = req.getParameter("setDeveloperId");

        String result = "";
        String error = "";

        try {
            ProjectDeveloperDaoService projectDeveloperDaoService = new ProjectDeveloperDaoService(connection);
            result = String.valueOf(projectDeveloperDaoService.exist(
                    Long.parseLong(setProjectId),
                    Long.parseLong(setDeveloperId)
            ));
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("result", result, "errorMessage", error)
        );

        engine.process("project_developer/project-developer-exit", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}