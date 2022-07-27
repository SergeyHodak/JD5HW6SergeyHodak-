package mvc.model.developer_skill;

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

public class DeveloperSkillGetAll implements Command {
    private final Connection connection;

    public DeveloperSkillGetAll(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        if (!Objects.equals(req.getMethod(), "POST")) {
            resp.setContentType("text/html");

            Context simpleContext = new Context(
                    req.getLocale(),
                    Map.of("developerSkills", "null", "errorMessage", "")
            );
            engine.process("developer_skill\\developer-skill-get-all", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }
        List<DeveloperSkill> developerSkills = new ArrayList<>();
        String error = "";

        try {
            DeveloperSkillDaoService developerSkillDaoService = new DeveloperSkillDaoService(connection);
            developerSkills = developerSkillDaoService.getAll();
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("developerSkills", developerSkills.size() == 0 ? "null" : developerSkills,"errorMessage", error)
        );
        engine.process("developer_skill\\developer-skill-get-all", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}