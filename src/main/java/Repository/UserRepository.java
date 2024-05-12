package Repository;

import database.DatabaseUtil;

import model.User;
import model.dto.CreateUserDto;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UserRepository {
    public static boolean create(CreateUserDto userData) {
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


    public static User getByEmail(String email) {
        String query = "SELECT * FROM USER WHERE email = ? LIMIT 1";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, email);
            ResultSet result = pst.executeQuery();
            if (result.next()) {
                return getFromResultSet(result);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private static User getFromResultSet(ResultSet result) {
        try {
            String firstName = result.getString("firstName");
            String lastName = result.getString("lastName");
            String email = result.getString("email");
            String salt = result.getString("salt");
            String passwordHash = result.getString("passwordHash");
            boolean isAdmin = result.getBoolean("isAdmin");
            return new User(
                    firstName, lastName, email, salt, passwordHash, isAdmin
            );
        } catch (Exception e) {
            return null;
        }
    }

}