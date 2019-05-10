package pl.sda.servlet;

import pl.sda.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "homeServlet", urlPatterns = {"", "/login"})
public class HomeServlet extends HttpServlet {

    private static final String COOKIE_USERNAME = "twitter_username";
    private static final String COOKIE_PASSWORD = "twitter_password";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String CHECKBOX_SELECTED = "on";
    private static final int SECONDS_IN_DAY = 86400;

    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = null;
        String password = null;
        if(request != null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(COOKIE_USERNAME)) {
                    cookie.setMaxAge(SECONDS_IN_DAY);
                    response.addCookie(cookie);
                    username = cookie.getValue();
                } else if (cookie.getName().equals(COOKIE_PASSWORD)) {
                    cookie.setMaxAge(SECONDS_IN_DAY);
                    response.addCookie(cookie);
                    password = cookie.getValue();
                }
            }
        }
        if (username != null && password != null) {
            request.setAttribute(USERNAME, username);
            request.setAttribute(PASSWORD, password);
            doPost(request, response);
        } else {
            request.getRequestDispatcher("WEB-INF/view/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);
        if (username == null && password == null) {
            username = (String) request.getAttribute(USERNAME);
            password = (String) request.getAttribute(PASSWORD);
        }
        String remember = request.getParameter("remember");
        if (userDao.isUserExists(username, password)) {
            request.getSession().setAttribute(USERNAME, username);
            if (remember != null && remember.equals(CHECKBOX_SELECTED)) {
                Cookie usernameCookie = new Cookie(COOKIE_USERNAME, username);
                Cookie passwordCookie = new Cookie(COOKIE_PASSWORD, password);
                usernameCookie.setMaxAge(SECONDS_IN_DAY);
                passwordCookie.setMaxAge(SECONDS_IN_DAY);
                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
            }
            request.getRequestDispatcher("messages").forward(request, response);
        } else {
            request.setAttribute("error", "Username or password incorrect");
            request.getRequestDispatcher("WEB-INF/view/login.jsp").forward(request, response);
        }
    }
}
