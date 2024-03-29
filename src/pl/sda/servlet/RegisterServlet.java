package pl.sda.servlet;

import pl.sda.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "registerServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        if(!password1.equals(password2)) {
            request.setAttribute("error", "Passwords are not equal");
            request.getRequestDispatcher("WEB-INF/view/register.jsp").forward(request, response);
        }

        if(userDao.isUserExists(username, password1)) {
            request.setAttribute("error", "User already exists");
            request.getRequestDispatcher("WEB-INF/view/register.jsp").forward(request, response);
        }

        userDao.insertUser(username, password1);
        request.getRequestDispatcher("login").forward(request, response);
    }
}
