package mvc.model.project_developer;

import mvc.model.Command;
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

public class ProjectDeveloperGetAllByProjectId  implements Command {
    private final Connection connection;

    public ProjectDeveloperGetAllByProjectId(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        if (!Objects.equals(req.getMethod(), "POST")) {
            resp.setContentType("text/html");

            Context simpleContext = new Context(
                    req.getLocale(),
                    Map.of("projectDevelopers", "null", "errorMessage", "")
            );
            engine.process("project_developer\\project-developer-get-all-by-project-id", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }

        String setProjectId = req.getParameter("setProjectId");

        List<ProjectDeveloper> projectDevelopers = new ArrayList<>();
        String error = "";

        try {
            ProjectDeveloperDaoService projectDeveloperDaoService = new ProjectDeveloperDaoService(connection);
            projectDevelopers = projectDeveloperDaoService.getAllByProjectId(Long.parseLong(setProjectId));
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("projectDevelopers", projectDevelopers.size() == 0 ? "null" : projectDevelopers,"errorMessage", error)
        );
        engine.process("project_developer\\project-developer-get-all-by-project-id", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}