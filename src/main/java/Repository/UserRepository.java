package repository;

import database.DatabaseUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Room;
import model.User;
import model.dto.CreateUserDto;
import model.dto.InsertRoomDto;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
            boolean isAdmin=result.getBoolean("isAdmin");
            return new User(
                    firstName, lastName, email, salt, passwordHash, isAdmin
            );
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean insert(InsertRoomDto roomData) {
        Connection conn = DatabaseUtil.getConnection();
        String query = """
                INSERT INTO ROOMS (roomNumber, floorNumber, roomType, capacity, bedNumber, price)
                VALUE (?, ?, ?, ?, ?, ?)
                """;
        //String query = "INSERT INTO USER VALUE (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, roomData.getRoomNumber());
            pst.setInt(2,roomData.getFloorNumber());
            pst.setString(3, roomData.getRoomType());
            pst.setInt(4, roomData.getCapacity());
            pst.setInt(5, roomData.getBedNumber());
            pst.setDouble(6, roomData.getPrice());
            pst.execute();
            pst.close();
            System.out.println("[ADDED]");
            return true;
        } catch (Exception e) {
            System.out.println("[ERROR] SQL did not execute "+e.getMessage());
            return false;
        }

    }

    public static Room getRoom(int roomNumber,int floor) {
        String query = "SELECT * FROM ROOMS WHERE roomNumber = ? AND floorNumber= ? LIMIT 1";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, roomNumber);
            pst.setInt(2, floor);
            ResultSet result = pst.executeQuery();
            if(result.next()){
                System.out.println("[ROOM EXIST]") ;
                return getRoomFromResultSet(result);
            };
            pst.close();
            return null;
        } catch (Exception e) {
            return null;
        }
    }


    public static ObservableList<Room> ListRoom() {
        ObservableList<Room> list= FXCollections.observableArrayList();
        String query = "SELECT * FROM ROOMS";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet result = pst.executeQuery();
           while (result.next()) {
                Room room= new Room(result.getInt("roomNumber"),
                        result.getInt("floorNumber"),
                        result.getString("roomType"),
                        result.getInt("capacity"),
                        result.getInt("bedNumber"),
                        result.getDouble("price"),
                        result.getBoolean("isAvailable"));
                list.add(room);
            };
            return list;
        } catch (Exception e) {
            return list;
        }
    }

    private static Room getRoomFromResultSet(ResultSet result) {
        try {
            int roomNumber = result.getInt("roomNumber");
            int floor = result.getInt("floorNumber");
            int capacity = result.getInt("capacity");
            int beds = result.getInt("bedNumber");
            double price = result.getDouble("price");
            boolean isAvailable = result.getBoolean("isAvailable");
            String roomType = result.getString("roomType");
            System.out.println("[RETURNING ROOM] "+roomNumber+" "+floor+" "+capacity+" "+beds+" "+price+" "+isAvailable+" "+roomType);
            return new Room(
                    roomNumber,floor,roomType,capacity,beds,price,isAvailable
            );
        } catch (Exception e) {
            System.out.println("[ERROR] "+e.getMessage());
            return null;
        }
    }

    public static int RoomsBooked() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            String query = "SELECT COUNT(*) as Rooms_booked FROM reservation WHERE reservationDate = ?";
            connection = DatabaseUtil.getConnection();
            statement = connection.prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            result = statement.executeQuery();
            if (result.next()) {
                System.out.println("Rooms booked updated successfully.");
                return result.getInt("Rooms_booked");
            }
            return 0;

        } catch (SQLException e) {
            System.out.println("[ERROR DB]" + e.getMessage());
            return 0;
        }
    }

}