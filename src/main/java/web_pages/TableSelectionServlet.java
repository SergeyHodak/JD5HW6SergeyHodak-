package web_pages;import org.thymeleaf.TemplateEngine;import org.thymeleaf.context.Context;import org.thymeleaf.templateresolver.FileTemplateResolver;import javax.servlet.ServletException;import javax.servlet.annotation.WebServlet;import javax.servlet.http.HttpServlet;import javax.servlet.http.HttpServletRequest;import javax.servlet.http.HttpServletResponse;import java.io.IOException;@WebServlet(value = "/table")public class TableSelectionServlet extends HttpServlet {    private TemplateEngine engine;    @Override    public void init() throws ServletException {        engine = new TemplateEngine();        FileTemplateResolver resolver = new FileTemplateResolver();        resolver.setPrefix("D:\\JavaSerge\\JD5HW6SergeyHodak\\src\\main\\java\\mvc\\view\\templates\\");        resolver.setSuffix(".html");        resolver.setTemplateMode("HTML5");        resolver.setOrder(engine.getTemplateResolvers().size());        resolver.setCacheable(false);        engine.addTemplateResolver(resolver);    }    @Override    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {        resp.setContentType("text/html");        Context simpleContext = new Context();        engine.process("start", simpleContext, resp.getWriter());        resp.getWriter().close();    }}