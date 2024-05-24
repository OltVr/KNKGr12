package service;

import Repository.*;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import model.Room;
import model.Staff;
import model.User;
import model.dto.InsertRoomDto;
import model.dto.InsertStaffDto;
import model.Reservation;
import model.filter.ReservationFilter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminService {
    public static boolean addRoom(InsertRoomDto roomData){
       Room room = RoomRepository.getRoom(roomData.getRoomNumber(), roomData.getFloorNumber());
       if (room==null){
           return RoomRepository.insertRoom(roomData);
       }
       return false;
    }

    public static boolean addStaff(InsertStaffDto staffData){
            return StaffRepository.insertStaff(staffData);
    }

    public static int newUsers() {
        return UserRepository.countNewUsers();
    }

    public static int RoomsBooked() {
        return RoomRepository.countRoomsBookedToday();
    }

    public static double TotalIncome() {
        return AdminRepository.calculateTotalIncome();
    }

    public static ObservableList<Room> ListRoom() {
        return RoomRepository.ListRoom();
    }

    public static ObservableList<Reservation> ListReservations() {
        return ReservationRepository.ListReservations();
    }

    public static boolean deleteReservation(int reservationID) {
        return ReservationRepository.deleteReservation(reservationID);
    }

    public static ObservableList<Reservation> searchReservations(String searchTerm, ReservationFilter filter) {
        return ReservationRepository.searchReservations(searchTerm, filter);
    }

    public static ObservableList<User> searchUsers(String searchTerm) {
        return UserRepository.searchUsers(searchTerm);
    }

    public static ObservableList<User> ListUser() {
        return UserRepository.ListUser();
    }

    public static boolean deleteRoom(int roomNumber, int floorNumber) {
        return RoomRepository.deleteRoom(roomNumber, floorNumber);
    }

    public static boolean deleteUser(String email){
        return UserRepository.deleteUser(email);
    }

    public static boolean updateRoom(InsertRoomDto roomData) {
        return RoomRepository.updateRoom(roomData);
    }

    public static ObservableList<Staff> ListStaff(){return StaffRepository.ListStaff();}

    public static ObservableList<Staff> searchStaff(String searchTerm){
        return StaffRepository.searchStaff(searchTerm);
    }

    public static boolean deleteStaff(String email){return StaffRepository.deleteStaff(email);}

    public static XYChart.Series<String, Number> getDailyIncomeSeries() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        try {
            ResultSet result = AdminRepository.getDailyIncomeData();
            while (result.next()) {
                series.getData().add(new XYChart.Data<>(result.getString("reservationDate"), result.getInt("total_price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return series;
    }
}
