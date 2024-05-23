package Repository;

import database.DatabaseUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import model.dto.CreateUserDto;

import java.sql.*;


public class UserRepository {

    public static boolean deleteUser(String email) {
        String query = "DELETE FROM user WHERE email = ?";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, email);
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            System.out.println("[DB ERROR] " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean userExists(String email) {
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ObservableList<User> searchUsers(String searchTerm) {
        ObservableList<User> userList = FXCollections.observableArrayList();
        String query = "SELECT * FROM user WHERE email LIKE ?";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, "%" + searchTerm + "%");
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                User user = new User(
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("email"),
                        result.getString("salt"),
                        result.getString("passwordHash"),
                        result.getBoolean("isAdmin"),
                        result.getTimestamp("createdAt"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
            return userList;
        }
    }

    public static ObservableList<User> ListUser(){
        ObservableList<User> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM USER";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                User user = new User(
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("email"),
                        result.getString("salt"),
                        result.getString("passwordHash"),
                        result.getBoolean("isAdmin"),
                        result.getTimestamp("CreatedAt"));
                list.add(user);
            }
            return list;
        } catch (Exception e) {
            return list;
        }
    }
    public static boolean createUser(CreateUserDto userData) {
        Connection conn = DatabaseUtil.getConnection();
        String query = """
                INSERT INTO USER (firstName, lastName, email, salt, passwordHash)
                VALUE (?, ?, ?, ?, ?)
                """;
        //String query = "INSERT INTO USER VALUE (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, userData.getFirstName());
            pst.setString(2, userData.getLastName());
            pst.setString(3, userData.getEmail());
            pst.setString(4, userData.getSalt());
            pst.setString(5, userData.getPasswordHash());
            pst.execute();
            pst.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static User getUserByEmail(String email) {
        String query = "SELECT * FROM USER WHERE email = ? LIMIT 1";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, email);
            ResultSet result = pst.executeQuery();
            if (result.next()) {
                return getUserFromResultSet(result);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private static User getUserFromResultSet(ResultSet result) {
        try {
            String firstName = result.getString("firstName");
            String lastName = result.getString("lastName");
            String email = result.getString("email");
            String salt = result.getString("salt");
            String passwordHash = result.getString("passwordHash");
            boolean isAdmin = result.getBoolean("isAdmin");
            Timestamp CreatedAt = result.getTimestamp("CreatedAt");
            return new User(
                    firstName, lastName, email, salt, passwordHash, isAdmin, CreatedAt
            );
        } catch (Exception e) {
            return null;
        }
    }

    public static int countNewUsers(){
        Connection conn=null;
        PreparedStatement statement=null;
        ResultSet result=null;
        try{
            String query="SELECT COUNT(*) AS user_count FROM user WHERE YEAR(CreatedAt) = YEAR(CURDATE()) AND isAdmin != 1";
            conn=DatabaseUtil.getConnection();
            statement=conn.prepareStatement(query);
            result=statement.executeQuery();

            if (result.next()){
                int users=result.getInt("user_count");
                return users;
            }
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }



}