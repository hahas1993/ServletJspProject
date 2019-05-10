package pl.sda.servlet;

import pl.sda.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "unfollowServlet", urlPatterns = {"/unfollow"})
public class UnfollowServlet extends HttpServlet {

    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        String usernameToUnfollow = request.getParameter("usernameToUnfollow");
        userDao.unfollow(username, usernameToUnfollow);
        request.getRequestDispatcher("users").forward(request, response);
    }
}
