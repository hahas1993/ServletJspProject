package pl.sda.servlet;

import pl.sda.dao.TweetDao;
import pl.sda.model.Tweet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "messagesServlet", urlPatterns = {"/messages"})
public class MessagesServlet extends HttpServlet {

    private TweetDao tweetDao = new TweetDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tweet> tweets = tweetDao.getFollowedTweets((String) request.getSession().getAttribute("username"));
        request.setAttribute("tweets", tweets);
        request.getRequestDispatcher("WEB-INF/view/messages.jsp").forward(request, response);
    }
}
