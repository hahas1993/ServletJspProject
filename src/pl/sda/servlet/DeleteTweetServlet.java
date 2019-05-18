package pl.sda.servlet;

import pl.sda.dao.TweetDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "deleteTweetServlet", urlPatterns = {"/deleteTweet"})
public class DeleteTweetServlet extends HttpServlet {

    private TweetDao tweetDao = new TweetDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        int tweetId = Integer.parseInt(request.getParameter("tweetId"));
        tweetDao.deleteTweet(tweetId, username);
        request.getRequestDispatcher("messages").forward(request, response);
    }
}
