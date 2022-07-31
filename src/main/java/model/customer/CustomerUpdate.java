package model.customer;

import model.Command;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import java.util.Objects;

public class CustomerUpdate implements Command {
    private final Connection connection;

    public CustomerUpdate(Connection connection) {
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
            engine.process("customer/customer-update", simpleContext, resp.getWriter());
            resp.getWriter().close();
            return;
        }

        String setId = req.getParameter("setId");
        String setFirstName = req.getParameter("setFirstName");
        String setSecondName = req.getParameter("setSecondName");
        String setAge = req.getParameter("setAge");

        String error = "";

        try {
            Customer customer = new Customer();
            customer.setId(Long.parseLong(setId));
            customer.setFirstName(setFirstName);
            customer.setSecondName(setSecondName);
            customer.setAge(Integer.parseInt(setAge));

            CustomerDaoService customerDaoService = new CustomerDaoService(connection);
            error = customerDaoService.update(customer);
        } catch (Exception e) {
            error = e.getMessage();
        }

        resp.setContentType("text/html");
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("errorMessage", error)
        );

        engine.process("customer/customer-update", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}