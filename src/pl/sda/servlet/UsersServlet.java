package pl.sda.servlet;

import pl.sda.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class UsersServlet extends HttpServlet {

    UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // if user has current session show his dashboard, if not show login page
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Set<String> followedUsers = userDao.getFollowedUsers((String) request.getSession().getAttribute("username"));
        Set<String> notFollowedUsers = userDao.getNotFollowedUsers((String) request.getSession().getAttribute("username"));
        request.setAttribute("followedUsers", followedUsers);
        request.setAttribute("notFollowedUsers", notFollowedUsers);
        request.getRequestDispatcher("WEB-INF/view/users.jsp").forward(request, response);
    }
}
