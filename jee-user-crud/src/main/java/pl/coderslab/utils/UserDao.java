package pl.coderslab.utils;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.users.User;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QERY = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";

    public User create(User user)  {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        }catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String hashPassword(String passwor) {
        return BCrypt.hashpw(passwor, BCrypt.gensalt());
    }

    private static final String READ_USER_QERY = "SELECT * FROM users WHERE id = (?);";
    public static User read(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_USER_QERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUserName(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            return user;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static final String UPDATE_USER = "UPDATE users set username = (?), email = (?), password = (?) WHERE id = (?);";
    public void update(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private static final String DELETE_USER = "DELETE from users WHERE id =(?);";

    public void deleteUser(int userid) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER);
            statement.setInt(1,userid);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static final String FIND_ALL = "SELECT * FROM users ";


    public User[] findALL() {
        try (Connection conn = DbUtil.getConnection()) {
            User[] users = new User[0];
            Statement staytment = conn.createStatement();
            ResultSet resultSet = staytment.executeQuery(FIND_ALL );
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                users = addToArray(user, users);
            }



            return users;
        }catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private User[] addToArray(User user, User[] usersList) {
        User[] tempUsers = Arrays.copyOf(usersList, usersList.length +1);
        tempUsers[tempUsers.length -1] = user;
        return tempUsers;
    }
}

