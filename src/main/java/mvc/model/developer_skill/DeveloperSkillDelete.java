package mvc.model.developer_skill;

import mvc.model.Command;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import java.util.Objects;

public class DeveloperSkillDelete implements Command {
    private final Connection connection;

    public DeveloperSkillDelete(Connection connection) {
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
            engine.process("developer_skill\\developer-skill-delete", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }

        String setDeveloperId = req.getParameter("setDeveloperId");
        String setSkillId = req.getParameter("setSkillId");

        String error = "";

        try {
            DeveloperSkill developerSkill = new DeveloperSkill();
            developerSkill.setDeveloperId(Long.parseLong(setDeveloperId));
            developerSkill.setSkillId(Long.parseLong(setSkillId));

            DeveloperSkillDaoService developerSkillDaoService = new DeveloperSkillDaoService(connection);
            error = developerSkillDaoService.delete(developerSkill);
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("errorMessage", error)
        );

        engine.process("developer_skill\\developer-skill-delete", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}