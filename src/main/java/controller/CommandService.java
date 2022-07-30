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

        commands.put("GET /app/table", new TableSelection());

        commands.put("GET /app/table/company", new CompanyPage());
        commands.put("GET /app/table/company/create", new CompanyCreate(connection));
        commands.put("POST /app/table/company/create", new CompanyCreate(connection));
        commands.put("GET /app/table/company/get-by-id", new CompanyGetById(connection));
        commands.put("POST /app/table/company/get-by-id", new CompanyGetById(connection));
        commands.put("GET /app/table/company/get-all", new CompanyGetAll(connection));
        commands.put("POST /app/table/company/get-all", new CompanyGetAll(connection));
        commands.put("GET /app/table/company/update", new CompanyUpdate(connection));
        commands.put("POST /app/table/company/update", new CompanyUpdate(connection));
        commands.put("GET /app/table/company/delete-by-id", new CompanyDeleteById(connection));
        commands.put("POST /app/table/company/delete-by-id", new CompanyDeleteById(connection));

        commands.put("GET /app/table/customer", new CustomerPage());
        commands.put("GET /app/table/customer/create", new CustomerCreate(connection));
        commands.put("POST /app/table/customer/create", new CustomerCreate(connection));
        commands.put("GET /app/table/customer/delete-by-id", new CustomerDeleteById(connection));
        commands.put("POST /app/table/customer/delete-by-id", new CustomerDeleteById(connection));
        commands.put("GET /app/table/customer/get-all", new CustomerGetAll(connection));
        commands.put("POST /app/table/customer/get-all", new CustomerGetAll(connection));
        commands.put("GET /app/table/customer/get-by-id", new CustomerGetById(connection));
        commands.put("POST /app/table/customer/get-by-id", new CustomerGetById(connection));
        commands.put("GET /app/table/customer/update", new CustomerUpdate(connection));
        commands.put("POST /app/table/customer/update", new CustomerUpdate(connection));

        commands.put("GET /app/table/project", new ProjectPage());
        commands.put("GET /app/table/project/create", new ProjectCreate(connection));
        commands.put("POST /app/table/project/create", new ProjectCreate(connection));
        commands.put("GET /app/table/project/delete-by-id", new ProjectDeleteById(connection));
        commands.put("POST /app/table/project/delete-by-id", new ProjectDeleteById(connection));
        commands.put("GET /app/table/project/get-all", new ProjectGetAll(connection));
        commands.put("POST /app/table/project/get-all", new ProjectGetAll(connection));
        commands.put("GET /app/table/project/get-by-id", new ProjectGetById(connection));
        commands.put("POST /app/table/project/get-by-id", new ProjectGetById(connection));
        commands.put("GET /app/table/project/update", new ProjectUpdate(connection));
        commands.put("POST /app/table/project/update", new ProjectUpdate(connection));
        commands.put("GET /app/table/project/get-cost-by-id", new ProjectGetCostById(connection));
        commands.put("POST /app/table/project/get-cost-by-id", new ProjectGetCostById(connection));
        commands.put("GET /app/table/project/update-cost-by-id", new ProjectUpdateCostById(connection));
        commands.put("POST /app/table/project/update-cost-by-id", new ProjectUpdateCostById(connection));
        commands.put("GET /app/table/project/get-developers-by-project-id", new ProjectGetDevelopersByProjectId(connection));
        commands.put("POST /app/table/project/get-developers-by-project-id", new ProjectGetDevelopersByProjectId(connection));
        commands.put("GET /app/table/project/get-all-by-special-format", new ProjectGetAllBySpecialFormat(connection));
        commands.put("POST /app/table/project/get-all-by-special-format", new ProjectGetAllBySpecialFormat(connection));

        commands.put("GET /app/table/developer", new DeveloperPage());
        commands.put("GET /app/table/developer/create", new DeveloperCreate(connection));
        commands.put("POST /app/table/developer/create", new DeveloperCreate(connection));
        commands.put("GET /app/table/developer/get-by-id", new DeveloperGetById(connection));
        commands.put("POST /app/table/developer/get-by-id", new DeveloperGetById(connection));
        commands.put("GET /app/table/developer/get-all", new DeveloperGetAll(connection));
        commands.put("POST /app/table/developer/get-all", new DeveloperGetAll(connection));
        commands.put("GET /app/table/developer/update", new DeveloperUpdate(connection));
        commands.put("POST /app/table/developer/update", new DeveloperUpdate(connection));
        commands.put("GET /app/table/developer/delete-by-id", new DeveloperDeleteById(connection));
        commands.put("POST /app/table/developer/delete-by-id", new DeveloperDeleteById(connection));
        commands.put("GET /app/table/developer/get-developers-by-department", new DeveloperGetDevelopersByDepartment(connection));
        commands.put("POST /app/table/developer/get-developers-by-department", new DeveloperGetDevelopersByDepartment(connection));
        commands.put("GET /app/table/developer/get-developers-by-skill-level", new DeveloperGetDevelopersBySkillLevel(connection));
        commands.put("POST /app/table/developer/get-developers-by-skill-level", new DeveloperGetDevelopersBySkillLevel(connection));

        commands.put("GET /app/table/project-developer", new ProjectDeveloperPage());
        commands.put("GET /app/table/project-developer/create", new ProjectDeveloperCreate(connection));
        commands.put("POST /app/table/project-developer/create", new ProjectDeveloperCreate(connection));
        commands.put("GET /app/table/project-developer/exist", new ProjectDeveloperExist(connection));
        commands.put("POST /app/table/project-developer/exist", new ProjectDeveloperExist(connection));
        commands.put("GET /app/table/project-developer/get-all", new ProjectDeveloperGetAll(connection));
        commands.put("POST /app/table/project-developer/get-all", new ProjectDeveloperGetAll(connection));
        commands.put("GET /app/table/project-developer/get-all-by-project-id", new ProjectDeveloperGetAllByProjectId(connection));
        commands.put("POST /app/table/project-developer/get-all-by-project-id", new ProjectDeveloperGetAllByProjectId(connection));
        commands.put("GET /app/table/project-developer/get-all-by-developer-id", new ProjectDeveloperGetAllByDeveloperId(connection));
        commands.put("POST /app/table/project-developer/get-all-by-developer-id", new ProjectDeveloperGetAllByDeveloperId(connection));
        commands.put("GET /app/table/project-developer/update", new ProjectDeveloperUpdate(connection));
        commands.put("POST /app/table/project-developer/update", new ProjectDeveloperUpdate(connection));
        commands.put("GET /app/table/project-developer/delete", new ProjectDeveloperDelete(connection));
        commands.put("POST /app/table/project-developer/delete", new ProjectDeveloperDelete(connection));

        commands.put("GET /app/table/skill", new SkillPage());
        commands.put("GET /app/table/skill/create", new SkillCreate(connection));
        commands.put("POST /app/table/skill/create", new SkillCreate(connection));
        commands.put("GET /app/table/skill/delete-by-id", new SkillDeleteById(connection));
        commands.put("POST /app/table/skill/delete-by-id", new SkillDeleteById(connection));
        commands.put("GET /app/table/skill/get-all", new SkillGetAll(connection));
        commands.put("POST /app/table/skill/get-all", new SkillGetAll(connection));
        commands.put("GET /app/table/skill/get-by-id", new SkillGetById(connection));
        commands.put("POST /app/table/skill/get-by-id", new SkillGetById(connection));
        commands.put("GET /app/table/skill/update", new SkillUpdate(connection));
        commands.put("POST /app/table/skill/update", new SkillUpdate(connection));

        commands.put("GET /app/table/developer-skill", new DeveloperSkillPage());
        commands.put("GET /app/table/developer-skill/create", new DeveloperSkillCreate(connection));
        commands.put("POST /app/table/developer-skill/create", new DeveloperSkillCreate(connection));
        commands.put("GET /app/table/developer-skill/delete", new DeveloperSkillDelete(connection));
        commands.put("POST /app/table/developer-skill/delete", new DeveloperSkillDelete(connection));
        commands.put("GET /app/table/developer-skill/exist", new DeveloperSkillExist(connection));
        commands.put("POST /app/table/developer-skill/exist", new DeveloperSkillExist(connection));
        commands.put("GET /app/table/developer-skill/get-all", new DeveloperSkillGetAll(connection));
        commands.put("POST /app/table/developer-skill/get-all", new DeveloperSkillGetAll(connection));
        commands.put("GET /app/table/developer-skill/get-all-by-developer-id", new DeveloperSkillGetAllByDeveloperId(connection));
        commands.put("POST /app/table/developer-skill/get-all-by-developer-id", new DeveloperSkillGetAllByDeveloperId(connection));
        commands.put("GET /app/table/developer-skill/get-all-by-skill-id", new DeveloperSkillGetAllBySkillId(connection));
        commands.put("POST /app/table/developer-skill/get-all-by-skill-id", new DeveloperSkillGetAllBySkillId(connection));
        commands.put("GET /app/table/developer-skill/update", new DeveloperSkillUpdate(connection));
        commands.put("POST /app/table/developer-skill/update", new DeveloperSkillUpdate(connection));
    }

    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        String requestUri = req.getRequestURI();
        String commandKey = req.getMethod() + " " + requestUri;

        commands.get(commandKey).process(req, resp, engine);
    }
}