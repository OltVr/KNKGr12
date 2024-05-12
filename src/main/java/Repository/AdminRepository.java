package Repository;

import database.DatabaseUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Room;
import model.dto.InsertRoomDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AdminRepository {
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
            pst.setInt(2, roomData.getFloorNumber());
            pst.setString(3, roomData.getRoomType());
            pst.setInt(4, roomData.getCapacity());
            pst.setInt(5, roomData.getBedNumber());
            pst.setDouble(6, roomData.getPrice());
            pst.execute();
            pst.close();
            System.out.println("[ADDED]");
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
            System.out.println("[RETURNING ROOM] " + roomNumber + " " + floor + " " + capacity + " " + beds + " " + price + " " + isAvailable + " " + roomType);
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
                System.out.println("[ROOM EXIST]");
                return getRoomFromResultSet(result);
            }
            ;
            pst.close();
            return null;
        } catch (Exception e) {
            return null;
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
            }
            ;
            return list;
        } catch (Exception e) {
            return list;
        }
    }
    public static int TotalIncome(){
        Connection con=null;
        PreparedStatement statement=null;
        ResultSet rez=null;

        try{
            String Query= "SELECT SUM(r.price) AS total_price FROM rooms r JOIN reservation res ON r.roomNumber = res.roomNumber";
            con=DatabaseUtil.getConnection();
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
            String query = "SELECT COUNT(*) as Rooms_booked FROM reservation WHERE reservationDate = ?";
            connection = DatabaseUtil.getConnection();
            statement = connection.prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
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
    public static boolean deleteRoom(int roomNumber, int floor) {
        String query = "DELETE FROM ROOMS WHERE roomNumber = ? AND floorNumber = ?";
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, roomNumber);
            pst.setInt(2, floor);
            pst.executeUpdate();
            pst.close();
            System.out.println("[DELETED ROOM] Room deleted successfully.");
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
            System.out.println("[UPDATE] Room updated successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println("[DB ERROR] " + e.getMessage());
            e.printStackTrace();
            return false;
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
}
