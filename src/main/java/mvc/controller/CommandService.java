package mvc.controller;

import mvc.model.*;
import org.thymeleaf.TemplateEngine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandService {
    private final Map<String, Command> commands;

    public CommandService() {
        commands = new HashMap<>();

        commands.put("GET /app/table", new TableSelection());
        commands.put("GET /app/table/company", new CompanyPage());
        commands.put("GET /app/table/company/create", new CompanyCreateGet());
        commands.put("POST /app/table/company/create", new CompanyCreatePost());
    }

    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String requestUri = req.getRequestURI();
        String commandKey = req.getMethod() + " " + requestUri;

        commands.get(commandKey).process(req, resp, engine);
    }
}