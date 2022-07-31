package controller;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import prefs.Prefs;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/")
public class TableController extends HttpServlet {
    private TemplateEngine engine;
    private CommandService commandService;

    @Override
    public void init() {
        new DatabaseInitService().initDb(Prefs.DB_URL, Prefs.DB_USERNAME, Prefs.DB_PASSWORD);

        engine = new TemplateEngine();

        String urlProject = getServletContext().getRealPath("").replace('\\', '/');

        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix(urlProject + "view/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);

        commandService = new CommandService(Prefs.DB_URL, Prefs.DB_USERNAME, Prefs.DB_PASSWORD);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        commandService.process(req, resp, engine);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        Context simpleContext = new Context();
        engine.process("start", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}