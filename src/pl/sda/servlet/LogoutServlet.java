package pl.sda.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "logoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    private static final String COOKIE_USERNAME = "twitter_username";
    private static final String COOKIE_PASSWORD = "twitter_password";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        for(Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals(COOKIE_USERNAME) || cookie.getName().equals(COOKIE_PASSWORD)) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        request.getRequestDispatcher("WEB-INF/view/login.jsp").forward(request, response);
    }
}
