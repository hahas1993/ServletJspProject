package pl.sda.servlet;

import pl.sda.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeServlet extends HttpServlet {

    UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // if user has current session show his dashboard, if not show login page
        request.getRequestDispatcher("WEB-INF/view/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("login");
        String password = request.getParameter("password");
        if(userDao.isUserExists(username, password)) {
            request.getSession().setAttribute("username", username);
            request.getRequestDispatcher("messages").forward(request, response);
        } else {
            request.setAttribute("error", "Username or password incorrect");
            request.getRequestDispatcher("WEB-INF/view/login.jsp").forward(request, response);
        }
    }
}
