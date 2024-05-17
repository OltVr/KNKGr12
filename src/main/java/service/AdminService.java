package service;

import javafx.collections.ObservableList;
import model.Room;
import model.Staff;
import model.User;
import model.dto.InsertRoomDto;
import Repository.AdminRepository;
import model.dto.InsertStaffDto;
import model.dto.ReservationDto;

public class AdminService {
    public static boolean addRoom(InsertRoomDto roomData){
       Room room=AdminRepository.getRoom(roomData.getRoomNumber(), roomData.getFloorNumber());
       if (room==null){
           return AdminRepository.insertRoom(roomData);
       }
       return false;
    }

    public static boolean addStaff(InsertStaffDto staffData){
            return AdminRepository.insertStaff(staffData);
    }

    public static int newUsers() {
        return AdminRepository.newUsers();
    }

    public static int RoomsBooked() {
        return AdminRepository.RoomsBooked();
    }

    public static double TotalIncome() {
        return AdminRepository.TotalIncome();
    }

    public static ObservableList<Room> ListRoom() {
        return AdminRepository.ListRoom();
    }

    public static ObservableList<ReservationDto> ListReservations() {
        return AdminRepository.ListReservations();
    }

    public static boolean deleteReservation(int reservationID) {
        return AdminRepository.deleteReservation(reservationID);
    }

    public static ObservableList<ReservationDto> searchReservations(String searchTerm) {
        return AdminRepository.searchReservations(searchTerm);
    }

    public static ObservableList<User> searchUsers(String searchTerm) {
        return AdminRepository.searchUsers(searchTerm);
    }

    public static ObservableList<User> ListUser() {
        return AdminRepository.ListUser();
    }

    public static boolean deleteRoom(int roomNumber, int floorNumber) {
        return AdminRepository.deleteRoom(roomNumber, floorNumber);
    }

    public static boolean deleteUser(String email){
        return AdminRepository.deleteUser(email);
    }

    public static boolean updateRoom(InsertRoomDto roomData) {
        return AdminRepository.updateRoom(roomData);
    }

    public static ObservableList<Staff> ListStaff(){return AdminRepository.ListStaff();}

    public static ObservableList<Staff> searchStaff(String searchTerm){
        return AdminRepository.searchStaff(searchTerm);
    }

    public static boolean deleteStaff(String email){return AdminRepository.deleteStaff(email);}

}
