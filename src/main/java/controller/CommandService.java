package controller;

import model.Command;
import model.TableSelection;
import model.company.*;
import model.customer.*;
import model.developer.*;
import model.developer_skill.*;
import model.project.*;
import model.project_developer.*;
import model.skill.*;
import org.thymeleaf.TemplateEngine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class CommandService {
    private Connection connection;
    private final Map<String, Command> commands;

    public CommandService(String url, String username, String password) {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        commands = new HashMap<>();

        commands.put("GET /", new TableSelection());

        commands.put("GET /company", new CompanyPage());
        commands.put("GET /company/create", new CompanyCreate(connection));
        commands.put("POST /company/create", new CompanyCreate(connection));
        commands.put("GET /company/get-by-id", new CompanyGetById(connection));
        commands.put("POST /company/get-by-id", new CompanyGetById(connection));
        commands.put("GET /company/get-all", new CompanyGetAll(connection));
        commands.put("POST /company/get-all", new CompanyGetAll(connection));
        commands.put("GET /company/update", new CompanyUpdate(connection));
        commands.put("POST /company/update", new CompanyUpdate(connection));
        commands.put("GET /company/delete-by-id", new CompanyDeleteById(connection));
        commands.put("POST /company/delete-by-id", new CompanyDeleteById(connection));

        commands.put("GET /customer", new CustomerPage());
        commands.put("GET /customer/create", new CustomerCreate(connection));
        commands.put("POST /customer/create", new CustomerCreate(connection));
        commands.put("GET /customer/delete-by-id", new CustomerDeleteById(connection));
        commands.put("POST /customer/delete-by-id", new CustomerDeleteById(connection));
        commands.put("GET /customer/get-all", new CustomerGetAll(connection));
        commands.put("POST /customer/get-all", new CustomerGetAll(connection));
        commands.put("GET /customer/get-by-id", new CustomerGetById(connection));
        commands.put("POST /customer/get-by-id", new CustomerGetById(connection));
        commands.put("GET /customer/update", new CustomerUpdate(connection));
        commands.put("POST /customer/update", new CustomerUpdate(connection));

        commands.put("GET /project", new ProjectPage());
        commands.put("GET /project/create", new ProjectCreate(connection));
        commands.put("POST /project/create", new ProjectCreate(connection));
        commands.put("GET /project/delete-by-id", new ProjectDeleteById(connection));
        commands.put("POST /project/delete-by-id", new ProjectDeleteById(connection));
        commands.put("GET /project/get-all", new ProjectGetAll(connection));
        commands.put("POST /project/get-all", new ProjectGetAll(connection));
        commands.put("GET /project/get-by-id", new ProjectGetById(connection));
        commands.put("POST /project/get-by-id", new ProjectGetById(connection));
        commands.put("GET /project/update", new ProjectUpdate(connection));
        commands.put("POST /project/update", new ProjectUpdate(connection));
        commands.put("GET /project/get-cost-by-id", new ProjectGetCostById(connection));
        commands.put("POST /project/get-cost-by-id", new ProjectGetCostById(connection));
        commands.put("GET /project/update-cost-by-id", new ProjectUpdateCostById(connection));
        commands.put("POST /project/update-cost-by-id", new ProjectUpdateCostById(connection));
        commands.put("GET /project/get-developers-by-project-id", new ProjectGetDevelopersByProjectId(connection));
        commands.put("POST /project/get-developers-by-project-id", new ProjectGetDevelopersByProjectId(connection));
        commands.put("GET /project/get-all-by-special-format", new ProjectGetAllBySpecialFormat(connection));
        commands.put("POST /project/get-all-by-special-format", new ProjectGetAllBySpecialFormat(connection));

        commands.put("GET /developer", new DeveloperPage());
        commands.put("GET /developer/create", new DeveloperCreate(connection));
        commands.put("POST /developer/create", new DeveloperCreate(connection));
        commands.put("GET /developer/get-by-id", new DeveloperGetById(connection));
        commands.put("POST /developer/get-by-id", new DeveloperGetById(connection));
        commands.put("GET /developer/get-all", new DeveloperGetAll(connection));
        commands.put("POST /developer/get-all", new DeveloperGetAll(connection));
        commands.put("GET /developer/update", new DeveloperUpdate(connection));
        commands.put("POST /developer/update", new DeveloperUpdate(connection));
        commands.put("GET /developer/delete-by-id", new DeveloperDeleteById(connection));
        commands.put("POST /developer/delete-by-id", new DeveloperDeleteById(connection));
        commands.put("GET /developer/get-developers-by-department", new DeveloperGetDevelopersByDepartment(connection));
        commands.put("POST /developer/get-developers-by-department", new DeveloperGetDevelopersByDepartment(connection));
        commands.put("GET /developer/get-developers-by-skill-level", new DeveloperGetDevelopersBySkillLevel(connection));
        commands.put("POST /developer/get-developers-by-skill-level", new DeveloperGetDevelopersBySkillLevel(connection));

        commands.put("GET /project-developer", new ProjectDeveloperPage());
        commands.put("GET /project-developer/create", new ProjectDeveloperCreate(connection));
        commands.put("POST /project-developer/create", new ProjectDeveloperCreate(connection));
        commands.put("GET /project-developer/exist", new ProjectDeveloperExist(connection));
        commands.put("POST /project-developer/exist", new ProjectDeveloperExist(connection));
        commands.put("GET /project-developer/get-all", new ProjectDeveloperGetAll(connection));
        commands.put("POST /project-developer/get-all", new ProjectDeveloperGetAll(connection));
        commands.put("GET /project-developer/get-all-by-project-id", new ProjectDeveloperGetAllByProjectId(connection));
        commands.put("POST /project-developer/get-all-by-project-id", new ProjectDeveloperGetAllByProjectId(connection));
        commands.put("GET /project-developer/get-all-by-developer-id", new ProjectDeveloperGetAllByDeveloperId(connection));
        commands.put("POST /project-developer/get-all-by-developer-id", new ProjectDeveloperGetAllByDeveloperId(connection));
        commands.put("GET /project-developer/update", new ProjectDeveloperUpdate(connection));
        commands.put("POST /project-developer/update", new ProjectDeveloperUpdate(connection));
        commands.put("GET /project-developer/delete", new ProjectDeveloperDelete(connection));
        commands.put("POST /project-developer/delete", new ProjectDeveloperDelete(connection));

        commands.put("GET /skill", new SkillPage());
        commands.put("GET /skill/create", new SkillCreate(connection));
        commands.put("POST /skill/create", new SkillCreate(connection));
        commands.put("GET /skill/delete-by-id", new SkillDeleteById(connection));
        commands.put("POST /skill/delete-by-id", new SkillDeleteById(connection));
        commands.put("GET /skill/get-all", new SkillGetAll(connection));
        commands.put("POST /skill/get-all", new SkillGetAll(connection));
        commands.put("GET /skill/get-by-id", new SkillGetById(connection));
        commands.put("POST /skill/get-by-id", new SkillGetById(connection));
        commands.put("GET /skill/update", new SkillUpdate(connection));
        commands.put("POST /skill/update", new SkillUpdate(connection));

        commands.put("GET /developer-skill", new DeveloperSkillPage());
        commands.put("GET /developer-skill/create", new DeveloperSkillCreate(connection));
        commands.put("POST /developer-skill/create", new DeveloperSkillCreate(connection));
        commands.put("GET /developer-skill/delete", new DeveloperSkillDelete(connection));
        commands.put("POST /developer-skill/delete", new DeveloperSkillDelete(connection));
        commands.put("GET /developer-skill/exist", new DeveloperSkillExist(connection));
        commands.put("POST /developer-skill/exist", new DeveloperSkillExist(connection));
        commands.put("GET /developer-skill/get-all", new DeveloperSkillGetAll(connection));
        commands.put("POST /developer-skill/get-all", new DeveloperSkillGetAll(connection));
        commands.put("GET /developer-skill/get-all-by-developer-id", new DeveloperSkillGetAllByDeveloperId(connection));
        commands.put("POST /developer-skill/get-all-by-developer-id", new DeveloperSkillGetAllByDeveloperId(connection));
        commands.put("GET /developer-skill/get-all-by-skill-id", new DeveloperSkillGetAllBySkillId(connection));
        commands.put("POST /developer-skill/get-all-by-skill-id", new DeveloperSkillGetAllBySkillId(connection));
        commands.put("GET /developer-skill/update", new DeveloperSkillUpdate(connection));
        commands.put("POST /developer-skill/update", new DeveloperSkillUpdate(connection));
    }

    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String requestUri = req.getRequestURI();
        String commandKey = req.getMethod() + " " + requestUri;

        commands.get(commandKey).process(req, resp, engine);
    }
}