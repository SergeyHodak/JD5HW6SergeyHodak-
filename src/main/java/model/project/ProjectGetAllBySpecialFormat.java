package model.project;

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

public class ProjectGetAllBySpecialFormat implements Command {
    private final Connection connection;

    public ProjectGetAllBySpecialFormat(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        if (!Objects.equals(req.getMethod(), "POST")) {
            resp.setContentType("text/html");

            Context simpleContext = new Context(
                    req.getLocale(),
                    Map.of("projects", "null", "errorMessage", "")
            );
            engine.process("project\\project-get-all", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }
        List<ProjectSpecialFormat> projects = new ArrayList<>();
        String error = "";

        try {
            ProjectDaoService projectDaoService = new ProjectDaoService(connection);
            projects = projectDaoService.getAllBySpecialFormat();
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("projects", projects.size() == 0 ? "null" : projects,"errorMessage", error)
        );
        engine.process("project\\project-get-all", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}