package pl.sda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class UserDao {

    public void insertUser(String username, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseConnection.initializeDatabase();
            statement = connection.prepareStatement("insert into USER(NAME, PASSWORD) values(?, ?)");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.cleanUp(connection, statement);
        }
    }

    public boolean isUserExists(String username, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.initializeDatabase();
            statement = connection.prepareStatement("select count(*) as cnt from USER where NAME=? and PASSWORD=?");
            statement.setString(1, username);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("cnt") == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.cleanUp(resultSet, connection, statement);
        }
    }

    public Set<String> getFollowedUsers(String followerName) {
        Set<String> followedUsers = new HashSet<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.initializeDatabase();
            statement = connection.prepareStatement("select USER_NAME from FOLLOWER where FOLLOWER_NAME=?");
            statement.setString(1, followerName);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                followedUsers.add(resultSet.getString("USER_NAME"));
            }
            return followedUsers;
        } catch (Exception e) {
            e.printStackTrace();
            return followedUsers;
        } finally {
            DatabaseConnection.cleanUp(resultSet, connection, statement);
        }
    }

    public Set<String> getNotFollowedUsers(String followerName) {
        Set<String> followedUsers = getFollowedUsers(followerName);
        Set<String> notFollowedUsers = new HashSet<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.initializeDatabase();
            statement = connection.prepareStatement("select NAME from USER");
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                String username = resultSet.getString("NAME");
                if(!username.equals(followerName) && !followedUsers.contains(username)) {
                    notFollowedUsers.add(username);
                }
            }
            return notFollowedUsers;
        } catch (Exception e) {
            e.printStackTrace();
            return notFollowedUsers;
        } finally {
            DatabaseConnection.cleanUp(resultSet, connection, statement);
        }
    }

    public void follow(String followerName, String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseConnection.initializeDatabase();
            statement = connection.prepareStatement("insert into FOLLOWER(FOLLOWER_NAME, USER_NAME) values(?, ?)");
            statement.setString(1, followerName);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.cleanUp(connection, statement);
        }
    }

    public void unfollow(String followerName, String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseConnection.initializeDatabase();
            statement = connection.prepareStatement("delete from FOLLOWER where FOLLOWER_NAME=? and USER_NAME=?");
            statement.setString(1, followerName);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.cleanUp(connection, statement);
        }
    }

    public void deleteUser(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseConnection.initializeDatabase();
            statement = connection.prepareStatement("delete from USER where NAME=?");
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.cleanUp(connection, statement);
        }
    }
}
