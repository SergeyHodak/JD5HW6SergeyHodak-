package model.project;

import model.Command;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class ProjectCreate implements Command {
    private final Connection connection;

    public ProjectCreate(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        if (!Objects.equals(req.getMethod(), "POST")) {
            resp.setContentType("text/html");

            Context simpleContext = new Context(
                    req.getLocale(),
                    Map.of("projectId", "", "errorMessage", "")
            );
            engine.process("project/project-create", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }

        String setName = req.getParameter("setName");
        String setCompanyId = req.getParameter("setCompanyId");
        String setCustomerId = req.getParameter("setCustomerId");
        String setCost = req.getParameter("setCost");
        String setCreationDate = req.getParameter("setCreationDate");

        String id = "";
        String error = "";

        try {
            Project project = new Project();
            project.setName(setName);
            project.setCompanyId(Long.parseLong(setCompanyId));
            project.setCustomerId(Long.parseLong(setCustomerId));
            project.setCost(Double.parseDouble(setCost));
            project.setCreationDate(setCreationDate.equals("now") ? LocalDate.now() : LocalDate.parse(setCreationDate));

            ProjectDaoService projectDaoService = new ProjectDaoService(connection);
            id = String.valueOf(projectDaoService.create(project));
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("projectId", id, "errorMessage", error)
        );

        engine.process("project/project-create", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}