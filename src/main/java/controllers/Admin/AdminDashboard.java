package controllers.Admin;

import App.Navigator;
import database.DatabaseUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Room;
import model.User;
import model.dto.InsertRoomDto;
import model.dto.ReservationDto;
import service.AdminService;
import javafx.scene.control.Alert;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminDashboard implements Initializable {
    private static String  pane;

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Sign Out?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            Navigator.navigate(me, Navigator.LOGIN_PAGE);
        }
    }

    // Rooms Management Page
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

    // Tabela Rooms
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

    //Tabela User
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

    // Admin Dashboard Page
    @FXML
    private Text txtNewUsers;
    @FXML
    private Text txtRoomsBooked;
    @FXML
    private Text txtTotalIncome;

    // Tabela Reservations
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
    private TableColumn<ReservationDto, Integer> Res_totalPrice_col;

    // chart
    @FXML
    private TextField searchField;
    @FXML
    private LineChart<String, Number> chartDashboard;
    @FXML
    private TextField searchFieldUser;

    private String anchorPane = "Dashboard";

    // ADMIN DASHBOARD
    private void populateChart(){
        Connection connect=null;
        PreparedStatement statement=null;
        ResultSet result=null;
        XYChart.Series chart=new XYChart.Series();
        try{
            String query="SELECT res.reservationDate, SUM(DATEDIFF(res.checkOutDate, res.checkInDate) * r.price) AS total_price FROM rooms r JOIN reservation res ON r.roomNumber = res.roomNumber WHERE res.reservationDate >= CURDATE() - INTERVAL 7 DAY GROUP BY res.reservationDate";
            connect= DatabaseUtil.getConnection();
            statement=connect.prepareStatement(query);
            result=statement.executeQuery();

            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
            chartDashboard.getData().add(chart);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateNewUsers(){
        String newUsers=String.valueOf(AdminService.newUsers());
        txtNewUsers.setText(newUsers);
    }


    private void updateRoomsBooked(){
        String count= String.valueOf(AdminService.RoomsBooked());
        txtRoomsBooked.setText(count);

    }

    private void updateTotalIncome(){
        String total=String.valueOf(AdminService.TotalIncome()+" $");
        txtTotalIncome.setText(total);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ROOM MANAGEMENT
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
            showAlert("Room not added", "A room with this number already exists");
        } else {
            showList();
            clear();
        }

    }

    // Nderrimi i faqeve me visibility prej scenebuilder
    private void setAllPanesInvisible() {
        dashboardPane.setVisible(false);
        reservationPane.setVisible(false);
        roomPane.setVisible(false);
        guestsPane.setVisible(false);
    }
    @FXML
    private void handleDashboard() {
        setAllPanesInvisible();
        dashboardPane.setVisible(true);
        pane = "Dashboard";
        Navigator.setCurrentVisibleSection("#dashboardPane");
    }

    @FXML
    private void handleReservations() {
        setAllPanesInvisible();
        reservationPane.setVisible(true);
        pane = "Reservations";
        Navigator.setCurrentVisibleSection("#reservationPane");
        System.out.println("[RESERVATION]");
    }

    @FXML
    private void handleRooms() {
        setAllPanesInvisible();
        roomPane.setVisible(true);
        pane = "Rooms";
        Navigator.setCurrentVisibleSection("#roomPane");
    }

    @FXML
    private void handleGuests() {
        setAllPanesInvisible();
        guestsPane.setVisible(true);
        pane = "Guests";
        Navigator.setCurrentVisibleSection("#guestsPane");
    }



    private void showList(){
        ObservableList<Room> listData = AdminService.ListRoom();

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
        ObservableList<ReservationDto> listData = AdminService.ListReservations();

        Res_reservationID_col.setCellValueFactory(new PropertyValueFactory<>("reservationID"));
        Res_email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        Res_roomNumber_col.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        Res_reservationDate_col.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));
        Res_checkInDate_col.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        Res_checkOutDate_col.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        Res_totalPrice_col.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        reservationTable.setItems(listData);
    }
    @FXML
    private void handleDeleteReservation() {
        ReservationDto selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this reservation?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                boolean success = AdminService.deleteReservation(selectedReservation.getReservationID());
                if (success) {
                    reservationTable.getItems().remove(selectedReservation);
                    System.out.println("[DELETE] Reservation ID: " + selectedReservation.getReservationID() + " has been deleted.");
                } else {
                    showAlert("Error", "Failed to delete reservation");
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
            ObservableList<ReservationDto> searchResults = AdminService.searchReservations(searchTerm);
            if (!searchResults.isEmpty()) {
                reservationTable.setItems(searchResults);
                System.out.println("[SEARCH] Results found for: " + searchTerm);
            } else {
                System.out.println("[SEARCH] No results found for: " + searchTerm);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No reservations found for the given ID");
                alert.showAndWait();
            }
        } else {
            System.out.println("[SEARCH] Search term is empty.");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a reservation ID.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleSearchUser() {
        String searchTerm = searchFieldUser.getText().trim();
        if (!searchTerm.isEmpty()) {
            ObservableList<User> searchResults = AdminService.searchUsers(searchTerm);
            if (!searchResults.isEmpty()) {
                userTable.setItems(searchResults);
                System.out.println("[SEARCH] Results found for: " + searchTerm);
            } else {
                System.out.println("[SEARCH] No results found for: " + searchTerm);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No users found for the given email address.");
                alert.showAndWait();
            }
        } else {
            System.out.println("[SEARCH] Search term is empty.");
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter an email address.");
            alert.showAndWait();
        }
    }

    private void showUserList(){
        ObservableList<User> listData = AdminService.ListUser();

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
        populateChart();
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
        if (AdminService.deleteRoom(roomNumber,floorNumber)){
            showList();
            clear();
        }
        else {
            showAlert("Error", "Couldn't find room.");
        }
    }
    @FXML
    private void handleDeleteUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this user?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                boolean success = AdminService.deleteUser(selectedUser.getEmail());
                if (success) {
                    userTable.getItems().remove(selectedUser);
                    System.out.println("[DELETE] User with email: " + selectedUser.getEmail() + " has been deleted.");
                } else {
                    showAlert("Error", "Failed to delete user.");
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

        if (AdminService.updateRoom(RoomData)){
            showList();
            clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Table was updated successfully.");
            alert.showAndWait();
        }
        else {
            showAlert("Error", "The room either does not exist or there was a database error.");
        }
    }


    @FXML
    private void handleChangeLanguage(ActionEvent ae){

        if (Navigator.locale.getLanguage().equals("en")){
            Navigator.changeLanguage(ae,"sq");
            System.out.println("[CHANGE] ALBANIAN");

        }
        else if  (Navigator.locale.getLanguage().equals("sq")){
            Navigator.changeLanguage(ae,"en");
            System.out.println("[CHANGE] ENGLISH");
        }
    }
}
