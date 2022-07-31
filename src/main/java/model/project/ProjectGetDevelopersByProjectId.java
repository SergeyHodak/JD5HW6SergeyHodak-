package model.project;

import model.Command;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import model.developer.Developer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProjectGetDevelopersByProjectId implements Command {
    private final Connection connection;

    public ProjectGetDevelopersByProjectId(Connection connection) {
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
            engine.process("project/project-get-developers-by-project-id", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }

        String id = req.getParameter("setId");

        List<Developer> developers = new ArrayList<>();
        String error = "";

        try {
            ProjectDaoService projectDaoService = new ProjectDaoService(connection);
            developers = projectDaoService.getDevelopersByProjectId(Long.parseLong(id));
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("developers", developers.size() == 0 ? "null" : developers,"errorMessage", error)
        );
        engine.process("project/project-get-developers-by-project-id", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}