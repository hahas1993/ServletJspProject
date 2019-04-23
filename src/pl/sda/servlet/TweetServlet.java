package pl.sda.servlet;

import pl.sda.dao.TweetDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TweetServlet extends HttpServlet {

    private TweetDao tweetDao = new TweetDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        String tweetMessage = request.getParameter("tweetMessage");
        tweetDao.insertTweet(username, tweetMessage);
        request.getRequestDispatcher("messages").forward(request, response);
    }
}
