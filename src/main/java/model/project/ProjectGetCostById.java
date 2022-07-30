package model.project;

import model.Command;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import java.util.Objects;

public class ProjectGetCostById implements Command {
    private final Connection connection;

    public ProjectGetCostById(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        if (!Objects.equals(req.getMethod(), "POST")) {
            resp.setContentType("text/html");

            Context simpleContext = new Context(
                    req.getLocale(),
                    Map.of("cost", "", "errorMessage", "")
            );
            engine.process("project\\project-get-cost-by-id", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }
        String id = req.getParameter("setId");
        double cost = 0;
        String error = "";

        try {
            ProjectDaoService projectDaoService = new ProjectDaoService(connection);
            cost = projectDaoService.getCostById(Long.parseLong(id));
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("cost", cost, "errorMessage", error)
        );
        engine.process("project\\project-get-cost-by-id", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}