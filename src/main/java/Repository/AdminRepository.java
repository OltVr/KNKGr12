package Repository;

import database.DatabaseUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Room;
import model.Staff;
import model.User;
import model.dto.InsertRoomDto;
import model.dto.InsertStaffDto;
import model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepository {

    //ROOMS
    public static boolean insertRoom(InsertRoomDto roomData) {
        Connection conn = DatabaseUtil.getConnection();
        String query = """
                INSERT INTO ROOMS (roomNumber, floorNumber, roomType, capacity, bedNumber, price)
                VALUE (?, ?, ?, ?, ?, ?)
                """;
        //String query = "INSERT INTO USER VALUE (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, roomData.getRoomNumber());
            pst.setInt(2, roomData.getFloorNumber());
            pst.setString(3, roomData.getRoomType());
            pst.setInt(4, roomData.getCapacity());
            pst.setInt(5, roomData.getBedNumber());
            pst.setDouble(6, roomData.getPrice());
            pst.execute();
            pst.close();
            return true;
        } catch (Exception e) {
            System.out.println("[ERROR] SQL did not execute " + e.getMessage());
            return false;
        }

    }

    public static boolean insertStaff(InsertStaffDto staffData) {
        Connection conn = DatabaseUtil.getConnection();
        String query = """
            INSERT INTO STAFF (firstName, lastName, email, position, salary, isFullTime, hasBenefits)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, staffData.getStaffFirstName());
            pst.setString(2, staffData.getStaffLastName());
            pst.setString(3, staffData.getStaffEmail());
            pst.setString(4, staffData.getPosition());
            pst.setDouble(5, staffData.getSalary());
            pst.setBoolean(6, staffData.isFullTime());
            pst.setBoolean(7, staffData.isHasBenefits());
            pst.execute();
            pst.close();
            conn.close();
            return true;
        } catch (Exception e) {
            System.out.println("[ERROR] SQL did not execute " + e.getMessage());
            return false;
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
            return new Room(
                    roomNumber, floor, roomType, capacity, beds, price, isAvailable
            );
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
            return null;
        }
    }
    public static Room getRoom(int roomNumber, int floor) {
        String query = "SELECT * FROM ROOMS WHERE roomNumber = ? AND floorNumber= ? LIMIT 1";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, roomNumber);
            pst.setInt(2, floor);
            ResultSet result = pst.executeQuery();
            if (result.next()) {
                return getRoomFromResultSet(result);
            }
            ;
            pst.close();
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean deleteRoom(int roomNumber, int floor) {
        String query = "DELETE FROM ROOMS WHERE roomNumber = ? AND floorNumber = ?";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, roomNumber);
            pst.setInt(2, floor);
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            System.out.println("[DB ERROR] " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateRoom(InsertRoomDto room) {
        String query = "UPDATE ROOMS SET floorNumber= ?, roomType= ?, capacity= ?, bedNumber= ?, price= ?  WHERE roomNumber = ?";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, room.getFloorNumber());
            pst.setString(2 ,room.getRoomType());
            pst.setInt(3, room.getCapacity());
            pst.setInt(4, room.getBedNumber());
            pst.setDouble(5, room.getPrice());
            pst.setInt(6, room.getRoomNumber());
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            System.out.println("[DB ERROR] " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Room> ListRoom() {
        ObservableList<Room> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM ROOMS";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                Room room = new Room(result.getInt("roomNumber"),
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

    //USERS
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

    //RESERVATION
    public static boolean deleteReservation(int reservationID) {
        String query = "DELETE FROM reservation WHERE reservationID = ?";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, reservationID);
            int result = pst.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static ObservableList<Reservation> searchReservations(String searchTerm) {
        ObservableList<Reservation> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM reservation WHERE email LIKE ? OR reservationID = ?";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, "%" + searchTerm + "%");
            pst.setInt(2, tryParseInt(searchTerm));
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                Reservation reservation = new Reservation(
                        result.getInt("reservationID"),
                        result.getString("email"),
                        result.getInt("roomNumber"),
                        result.getDate("reservationDate"),
                        result.getDate("checkInDate"),
                        result.getDate("checkOutDate"),
                        result.getInt("totalPrice"));
                list.add(reservation);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
    }

    public static ObservableList<Reservation> ListReservations() {
        ObservableList<Reservation> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM reservation";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                Reservation reservation = new Reservation(
                        result.getInt("reservationID"),
                        result.getString("email"),
                        result.getInt("roomNumber"),
                        result.getDate("reservationDate"),
                        result.getDate("checkInDate"),
                        result.getDate("checkOutDate"),
                        result.getInt("totalPrice"));
                list.add(reservation);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
    }


    private static int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1; // Default to -1 nese fails
        }
    }

    public static int TotalIncome(){
        Connection con=null;
        PreparedStatement statement=null;
        ResultSet rez=null;

        try{
            String Query= "SELECT SUM(DATEDIFF(res.checkOutDate, res.checkInDate) * r.price) AS total_price FROM rooms r JOIN reservation res ON r.roomNumber = res.roomNumber";
            con= DatabaseUtil.getConnection();
            statement=con.prepareStatement(Query);
            rez=statement.executeQuery();

            if (rez.next()){
                int price= rez.getInt("total_price");
                return price;
            }
            return 0;
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static int RoomsBooked() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            String query = "SELECT COUNT(*) as Rooms_booked FROM reservation WHERE DATE(reservationDate) = CURDATE()";
            connection = DatabaseUtil.getConnection();
            statement = connection.prepareStatement(query);
            result = statement.executeQuery();
            if (result.next()) {

                return result.getInt("Rooms_booked");
            }
            return 0;

        } catch (SQLException e) {
            System.out.println("[ERROR DB]" + e.getMessage());
            return 0;
        }
    }

    public static int newUsers(){
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
    //Staff table
    public static ObservableList<Staff> ListStaff(){
        ObservableList<Staff> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM staff";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                Staff staff = new Staff(
                        result.getInt("staffID"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("email"),
                        result.getString("position"),
                        result.getDouble("salary"),
                        result.getBoolean("isEmployed"),
                        result.getBoolean("isFullTime"),
                        result.getBoolean("hasBenefits"),
                        result.getTimestamp("createdAt"));
                list.add(staff);
            }
            return list;
        } catch (Exception e) {
            return list;
        }
    }

    public static ObservableList<Staff> searchStaff(String searchTerm) {
        ObservableList<Staff> staffList = FXCollections.observableArrayList();
        String query = "SELECT * FROM staff WHERE email LIKE ?";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, "%" + searchTerm + "%");
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                Staff staff = new Staff(
                        result.getInt("staffID"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("email"),
                        result.getString("position"),
                        result.getDouble("salary"),
                        result.getBoolean("isEmployed"),
                        result.getBoolean("isFullTime"),
                        result.getBoolean("hasBenefits"),
                        result.getTimestamp("createdAt"));
                staffList.add(staff);
            }
            return staffList;
        } catch (SQLException e) {
            e.printStackTrace();
            return staffList;
        }
    }

    public static boolean deleteStaff(String email) {
        String query = "DELETE FROM staff WHERE email = ?";
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


}
