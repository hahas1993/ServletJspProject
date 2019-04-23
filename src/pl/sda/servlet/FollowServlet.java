package pl.sda.servlet;

import pl.sda.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FollowServlet extends HttpServlet {

    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // if user has current session show his dashboard, if not show login page
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        String usernameToFollow = request.getParameter("usernameToFollow");
        userDao.follow(username, usernameToFollow);
        request.getRequestDispatcher("users").forward(request, response);
    }
}
