package controllers;

import App.Navigator;
import Repository.AdminRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.Room;
import model.User;
import model.dto.InsertRoomDto;
import model.dto.ReservationDto;
import service.AdminService;

import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AdminDashboard implements Initializable {
    @FXML
    private AnchorPane dashboardPane;

    @FXML
    private AnchorPane roomPane;

    @FXML
    private AnchorPane reservationPane;

    @FXML
    private AnchorPane guestsPane;

    @FXML
    private void handleLogout(MouseEvent me) {
        Navigator.navigate(me,Navigator.LOGIN_PAGE);
    }

    @FXML
    private TextField txtRoom;

    @FXML
    private TextField txtFloor;

    @FXML
    private TextField txtPrice;

    @FXML
    private ComboBox<String> roomType;

    @FXML
    private ComboBox<Integer> Capacity;

    @FXML
    private ComboBox<Integer> Beds;

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, String> roomNumber_col;

    @FXML
    private TableColumn<Room, String> floorNumber_col;

    @FXML
    private TableColumn<Room, String> roomType_col;

    @FXML
    private TableColumn<Room, String> bedNumber_col;


    @FXML
    private TableColumn<Room, String> price_col;

    @FXML
    private TableColumn<Room, String> capacity_col;

    @FXML
    private TableColumn<Room, String> Available_col;

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> email_col;

    @FXML
    private TableColumn<User, String> firstName_col;

    @FXML
    private TableColumn<User, String> lastName_col;

    @FXML
    private TableColumn<User, String> isAdmin_col;

    @FXML
    private TableColumn<User, Timestamp> CreatedAt_col;
    @FXML
    private Text txtNewUsers;
    @FXML
    private Text txtRoomsBooked;
    @FXML
    private Text txtTotalIncome;
    @FXML
    private TableView<ReservationDto> reservationTable;

    @FXML
    private TableColumn<ReservationDto, Integer> Res_reservationID_col;
    @FXML
    private TableColumn<ReservationDto, String> Res_email_col;
    @FXML
    private TableColumn<ReservationDto, Integer> Res_roomNumber_col;
    @FXML
    private TableColumn<ReservationDto, Date> Res_reservationDate_col;
    @FXML
    private TableColumn<ReservationDto, Date> Res_checkInDate_col;
    @FXML
    private TableColumn<ReservationDto, Date> Res_checkOutDate_col;
    @FXML
    private TableColumn<ReservationDto, Integer> Res_numberOfPeople_col;

    @FXML
    private TextField searchField;




    private void updateNewUsers(){
        String newUsers=String.valueOf(AdminRepository.newUsers());
        txtNewUsers.setText(newUsers);
    }


    private void updateRoomsBooked(){
        String count= String.valueOf(AdminRepository.RoomsBooked());
        txtRoomsBooked.setText(count);

    }

    private void updateTotalIncome(){
        String total=String.valueOf(AdminRepository.TotalIncome()+" $");
        txtTotalIncome.setText(total);
    }

    @FXML
    private void handleAdd(){
        InsertRoomDto RoomData = new InsertRoomDto(
                Integer.parseInt(this.txtRoom.getText()),
                Integer.parseInt(this.txtFloor.getText()),
                this.roomType.getSelectionModel().getSelectedItem(),
                this.Capacity.getSelectionModel().getSelectedItem(),
                this.Beds.getSelectionModel().getSelectedItem(),
                Double.parseDouble(this.txtPrice.getText())
        );

        boolean b = AdminService.addRoom(RoomData);
        if (!(b)) {
            System.out.println("Room already exists");
        } else {
            showList();
            clear();
        }

    }

    @FXML
    private void handleDashboard(){
        dashboardPane.setVisible(true);
        reservationPane.setVisible(false);
        roomPane.setVisible(false);
        guestsPane.setVisible(false);
    }

    @FXML
    private void handleReservations(){
        dashboardPane.setVisible(false);
        reservationPane.setVisible(true);
        roomPane.setVisible(false);
        guestsPane.setVisible(false);
    }

    @FXML
    private void handleRooms(){
        dashboardPane.setVisible(false);
        reservationPane.setVisible(false);
        roomPane.setVisible(true);
        guestsPane.setVisible(false);
    }

    @FXML
    private void handleGuests(){
        dashboardPane.setVisible(false);
        reservationPane.setVisible(false);
        roomPane.setVisible(false);
        guestsPane.setVisible(true);
    }



    private void showList(){
        ObservableList<Room> listData = AdminRepository.ListRoom();

        roomNumber_col.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        floorNumber_col.setCellValueFactory(new PropertyValueFactory<>("floorNumber"));
        roomType_col.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        capacity_col.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        bedNumber_col.setCellValueFactory(new PropertyValueFactory<>("bedNumber"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        Available_col.setCellValueFactory(cellData -> {
            Room room = cellData.getValue();
            String availability = room.isAvailable() ? "Available" : "Unavailable";
            return new SimpleStringProperty(availability);
        });

        roomTable.setItems(listData);
    }
    private void showReservationList() {
        ObservableList<ReservationDto> listData = AdminRepository.ListReservations();

        Res_reservationID_col.setCellValueFactory(new PropertyValueFactory<>("reservationID"));
        Res_email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        Res_roomNumber_col.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        Res_reservationDate_col.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));
        Res_checkInDate_col.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        Res_checkOutDate_col.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        Res_numberOfPeople_col.setCellValueFactory(new PropertyValueFactory<>("numberOfPeople"));

        reservationTable.setItems(listData);
    }
    @FXML
    private void handleDeleteReservation() {
        ReservationDto selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this reservation?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                boolean success = AdminRepository.deleteReservation(selectedReservation.getReservationID());
                if (success) {
                    reservationTable.getItems().remove(selectedReservation);
                    System.out.println("[DELETE] Reservation ID: " + selectedReservation.getReservationID() + " has been deleted.");
                } else {
                    System.out.println("[ERROR] Failed to delete reservation.");
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No reservation selected.");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleSearchReservation() {
        String searchTerm = searchField.getText().trim();
        if (!searchTerm.isEmpty()) {
            ObservableList<ReservationDto> searchResults = AdminRepository.searchReservations(searchTerm);
            if (!searchResults.isEmpty()) {
                reservationTable.setItems(searchResults);
                System.out.println("[SEARCH] Results found for: " + searchTerm);
            } else {
                System.out.println("[SEARCH] No results found for: " + searchTerm);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No reservations found for the given search term.");
                alert.showAndWait();
            }
        } else {
            System.out.println("[SEARCH] Search term is empty.");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a search term.");
            alert.showAndWait();
        }
    }

    private void showUserList(){
        ObservableList<User> listData = AdminRepository.ListUser();

        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        firstName_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName_col.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        isAdmin_col.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            String role = user.isAdmin() ? "Admin" : "User";
            return new SimpleStringProperty(role);
        });
        CreatedAt_col.setCellValueFactory(new PropertyValueFactory<>("CreatedAt"));

        userTable.setItems(listData);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomType.getItems().addAll("Sea View", "City View");
        Capacity.getItems().addAll(1, 2,3,4,5,6,7,8);
        Beds.getItems().addAll(1, 2,3,4);
        showList();
        showUserList();
        updateRoomsBooked();
        updateTotalIncome();
        updateNewUsers();
        showReservationList();
    }

    private void clear(){
        txtRoom.setText("");
        txtFloor.setText("");
        txtPrice.setText("");
        roomType.getSelectionModel().clearSelection();
        roomType.setPromptText("Type");
        Capacity.getSelectionModel().clearSelection();
        Capacity.setPromptText("Capacity");
        Beds.getSelectionModel().clearSelection();
        Beds.setPromptText("Number of Beds");
    }

    @FXML
    private void  roomSelect(){
        Room room = roomTable.getSelectionModel().getSelectedItem();
//        int num = roomTable.getSelectionModel().getSelectedIndex();

        if (room!=null){
        txtRoom.setText(String.valueOf(room.getRoomNumber()));
        txtFloor.setText(String.valueOf(room.getFloorNumber()));
        txtPrice.setText(String.valueOf(room.getPrice()));}

    }

    @FXML
    private void handleDeleteRoom(){
        int roomNumber= Integer.parseInt(txtRoom.getText());
        int floorNumber= Integer.parseInt(txtFloor.getText());
        if (AdminRepository.deleteRoom(roomNumber,floorNumber)){
            showList();
            clear();
        }
        else {
            System.out.println("[ERROR] Couldn't find room");
        }
    }
    @FXML
    private void handleDeleteUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this user?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                boolean success = AdminRepository.deleteUser(selectedUser.getEmail());
                if (success) {
                    userTable.getItems().remove(selectedUser);
                    System.out.println("[DELETE] User with email: " + selectedUser.getEmail() + " has been deleted.");
                } else {
                    System.out.println("[ERROR] Failed to delete user.");
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No user selected.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleUpdate(){
        InsertRoomDto RoomData = new InsertRoomDto(
                Integer.parseInt(this.txtRoom.getText()),
                Integer.parseInt(this.txtFloor.getText()),
                this.roomType.getSelectionModel().getSelectedItem(),
                this.Capacity.getSelectionModel().getSelectedItem(),
                this.Beds.getSelectionModel().getSelectedItem(),
                Double.parseDouble(this.txtPrice.getText())
        );

        if (AdminRepository.updateRoom(RoomData)){
            showList();
            clear();
            System.out.println("[UPDATE] Table has been updated");
        }
        else {
            System.out.println("[ERROR] The room either does not exist or there was a [DB ERROR]");
        }
    }
}
