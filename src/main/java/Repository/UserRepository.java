package Repository;

import database.DatabaseUtil;

import model.Room;
import model.User;
import model.dto.CreateReservationDto;
import model.dto.CreateUserDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


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
            Timestamp CreatedAt = result.getTimestamp("CreatedAt");
            return new User(
                    firstName, lastName, email, salt, passwordHash, isAdmin, CreatedAt
            );
        } catch (Exception e) {
            return null;
        }
    }

    public static ObservableList<Room> listSeaViewRooms() {
        ObservableList<Room> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM rooms WHERE roomType = 'Sea View' AND isAvailable = True";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                Room room = new Room(
                        result.getInt("roomNumber"),
                        result.getInt("floorNumber"),
                        result.getString("roomType"),
                        result.getInt("capacity"),
                        result.getInt("bedNumber"),
                        result.getDouble("price"),
                        result.getBoolean("isAvailable"));
                list.add(room);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
    }

    public static ObservableList<Room> listCityViewRooms() {
        ObservableList<Room> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM rooms WHERE roomType = 'City View' AND isAvailable = True";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                Room room = new Room(
                        result.getInt("roomNumber"),
                        result.getInt("floorNumber"),
                        result.getString("roomType"),
                        result.getInt("capacity"),
                        result.getInt("bedNumber"),
                        result.getDouble("price"),
                        result.getBoolean("isAvailable"));
                list.add(room);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
    }

    public static boolean reserve(CreateReservationDto reservationData) {
        Connection conn = DatabaseUtil.getConnection();
        String query = """
                INSERT INTO RESERVATION (email, roomNumber,checkInDate ,checkOutDate)
                VALUE (?, ?, ?, ?)
                """;
        //String query = "INSERT INTO USER VALUE (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, reservationData.getUserEmail());
            pst.setInt(2, reservationData.getRoomNumber());
            pst.setDate(3, Date.valueOf(reservationData.getCheckInDate()));
            pst.setDate(4, Date.valueOf(reservationData.getCheckOutDate()));
            pst.execute();
            pst.close();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }


}