package Repository;

import database.DatabaseUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Reservation;
import model.dto.CreateReservationDto;

import java.sql.*;

public class ReservationRepository {
    // ADMIN SIDE
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

    private static int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1; // Default to -1 nese fails
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

    // USER SIDE
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
            System.out.println("[SQL] "+e.getMessage());
            return false;
        }

    }

    public static ObservableList<Reservation> listUserReservations() {
        ObservableList<Reservation> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM RESERVATION WHERE checkInDate >= CURDATE()";
        Connection connection = DatabaseUtil.getConnection();
        try (
                PreparedStatement pst = connection.prepareStatement(query);
                ResultSet result = pst.executeQuery()) {
            while (result.next()) {
                Reservation reservation = new Reservation(
                        result.getInt("reservationID"),
                        result.getString("email"),
                        result.getInt("roomNumber"),
                        result.getDate("reservationDate"),
                        result.getDate("checkInDate"),
                        result.getDate("checkOutDate"),
                        result.getDouble("totalPrice"));
                list.add(reservation);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("[SQL RESER] "+ e.getMessage());
            e.printStackTrace();
            return list;
        }
    }

    public static ObservableList<Reservation> listUserReservationHistory() {
        ObservableList<Reservation> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM RESERVATION WHERE checkOutDate <= CURDATE()";
        Connection connection = DatabaseUtil.getConnection();
        try (
                PreparedStatement pst = connection.prepareStatement(query);
                ResultSet result = pst.executeQuery()) {

            while (result.next()) {
                Reservation reservation = new Reservation(
                        result.getInt("reservationID"),
                        result.getString("email"),
                        result.getInt("roomNumber"),
                        result.getDate("reservationDate"),
                        result.getDate("checkInDate"),
                        result.getDate("checkOutDate"),
                        result.getDouble("totalPrice"));
                list.add(reservation);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("[SQL HIST] "+ e.getMessage());
            e.printStackTrace();
            return list;
        }
    }

    public static int getReservationCountForUser(String email){
        Connection connection = null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        int count = 0;

        try {
            String query="SELECT COUNT(*) AS reservation_count FROM reservation res JOIN user u ON res.email = u.email WHERE u.email = ?";
            connection=DatabaseUtil.getConnection();
            statement=connection.prepareStatement(query);
            statement.setString(1,email);
            resultSet=statement.executeQuery();

            if(resultSet.next()){
                count=resultSet.getInt("reservation_count");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }
}