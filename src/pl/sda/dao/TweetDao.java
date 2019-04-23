package pl.sda.dao;

import pl.sda.model.Tweet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TweetDao {

    UserDao userDao = new UserDao();

    public void insertTweet(String username, String message) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseConnection.initializeDatabase();
            statement = connection.prepareStatement("insert into TWEET(USER_NAME, MESSAGE) values(?, ?)");
            statement.setString(1, username);
            statement.setString(2, message);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.cleanUp(connection, statement);
        }
    }

    public List<Tweet> getFollowedTweets(String username) {
        Set<String> followedUsers = userDao.getFollowedUsers(username);
        followedUsers.add(username);
        List<Tweet> tweets = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.initializeDatabase();
            statement = connection.prepareStatement("select * from TWEET where USER_NAME IN('"
                    + String.join("','", followedUsers) + "')");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tweet tweet = new Tweet();
                tweet.setId(resultSet.getInt("ID"));
                tweet.setUsername(resultSet.getString("USER_NAME"));
                tweet.setPublishedAt(resultSet.getTimestamp("PUBLISHED_AT"));
                tweet.setMessage(resultSet.getString("MESSAGE"));
                tweets.add(tweet);
            }
            return tweets;
        } catch (Exception e) {
            e.printStackTrace();
            return tweets;
        } finally {
            DatabaseConnection.cleanUp(resultSet, connection, statement);
        }
    }
}
