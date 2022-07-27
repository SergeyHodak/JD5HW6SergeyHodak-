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

public class DeveloperSkillUpdate implements Command {
    private final Connection connection;

    public DeveloperSkillUpdate(Connection connection) {
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
            engine.process("developer_skill\\developer-skill-update", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }

        String setOldDeveloperId = req.getParameter("setOldDeveloperId");
        String setOldSkillId = req.getParameter("setOldSkillId");
        String setNewDeveloperId = req.getParameter("setNewDeveloperId");
        String setNewSkillId = req.getParameter("setNewSkillId");

        String error = "";

        try {
            DeveloperSkill newDeveloperSkill = new DeveloperSkill();
            newDeveloperSkill.setDeveloperId(Long.parseLong(setNewDeveloperId));
            newDeveloperSkill.setSkillId(Long.parseLong(setNewSkillId));

            DeveloperSkillDaoService developerSkillDaoService = new DeveloperSkillDaoService(connection);
            error = developerSkillDaoService.update(
                    Long.parseLong(setOldDeveloperId),
                    Long.parseLong(setOldSkillId),
                    newDeveloperSkill
            );
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("errorMessage", error)
        );

        engine.process("developer_skill\\developer-skill-update", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}