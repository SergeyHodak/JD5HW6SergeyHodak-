package model.developer_skill;

import model.Command;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import java.util.Objects;

public class DeveloperSkillCreate implements Command {
    private final Connection connection;

    public DeveloperSkillCreate(Connection connection) {
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
            engine.process("developer_skill/developer-skill-create", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }

        String setDeveloperId = req.getParameter("setDeveloperId");
        String setSkillId = req.getParameter("setSkillId");

        String result = "";
        String error = "";

        try {
            DeveloperSkill developerSkill = new DeveloperSkill();
            developerSkill.setDeveloperId(Long.parseLong(setDeveloperId));
            developerSkill.setSkillId(Long.parseLong(setSkillId));

            DeveloperSkillDaoService developerSkillDaoService = new DeveloperSkillDaoService(connection);
            result = String.valueOf(developerSkillDaoService.create(developerSkill));
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("result", result, "errorMessage", error)
        );

        engine.process("developer_skill/developer-skill-create", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}