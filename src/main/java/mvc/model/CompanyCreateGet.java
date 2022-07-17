package mvc.model;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class CompanyCreateGet implements Command{
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        resp.setContentType("text/html");


        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("companyId", "", "errorMessage", "")
        );
        engine.process("company-create", simpleContext, resp.getWriter());

        resp.sendRedirect("/app/company/create");
        resp.getWriter().close();
    }
}