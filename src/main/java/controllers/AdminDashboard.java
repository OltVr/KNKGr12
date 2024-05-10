package controllers;

import App.Navigator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Room;
import model.dto.InsertRoomDto;
import repository.UserRepository;
import service.AdminService;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboard implements Initializable {
    @FXML
    private AnchorPane dashboardPane;

    @FXML
    private AnchorPane roomPane;

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
        }

    }

    @FXML
    private void handleDashboard(){
        dashboardPane.setVisible(true);
        roomPane.setVisible(false);
    }

    @FXML
    private void handleRooms(){
        dashboardPane.setVisible(false);
        roomPane.setVisible(true);
    }

    private void showList(){
        ObservableList<Room> listData = UserRepository.ListRoom();

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
    }
}
