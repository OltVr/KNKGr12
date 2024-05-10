package controllers;

import App.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomType.getItems().addAll("Sea View", "City View");
        Capacity.getItems().addAll(1, 2,3,4,5,6,7,8);
        Beds.getItems().addAll(1, 2,3,4);

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
            System.out.println("Room added sucssesfully");
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
}
