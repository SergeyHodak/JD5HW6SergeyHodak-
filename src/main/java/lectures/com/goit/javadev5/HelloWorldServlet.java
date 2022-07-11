package lectures.com.goit.javadev5;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/hello") // Вказати шлях на який ми це все хочемо обробляти
public class HelloWorldServlet extends HttpServlet {

    // init - для тесту виведе наш сіаут
    @Override
    public void init() throws ServletException {
        super.init();

        System.out.println("Servlet init!");
    }

    // req - те що нам прийшло, resp - те куди ми записуємо відповідь
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // відправити в відповідь на запит в форматі інтернет сторінки
        resp.setContentType("text/html; charset=utf-8");
        // наповнити поток такими данними
        resp.getWriter().write("<h1>Привіт Servlets!</h1>");
        // закрити поток
        resp.getWriter().close();
    }
}