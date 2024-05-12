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
import model.dto.InsertRoomDto;
import service.AdminService;

import java.net.URL;
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
    private Text txtRoomsBooked;
    @FXML
    private Text txtTotalIncome;
    @FXML
    private Text txtNewUsers;

    private void updateNewUsers(){
        String totalNewUsers= String.valueOf(AdminRepository.newUsers());
        txtNewUsers.setText(totalNewUsers);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomType.getItems().addAll("Sea View", "City View");
        Capacity.getItems().addAll(1, 2,3,4,5,6,7,8);
        Beds.getItems().addAll(1, 2,3,4);
        showList();
        updateRoomsBooked();
        updateTotalIncome();
        updateNewUsers();
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
