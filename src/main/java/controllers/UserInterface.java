package controllers;

import App.Navigator;
import Repository.AdminRepository;
import Repository.UserRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Room;

import java.net.URL;
import java.util.ResourceBundle;


public class UserInterface implements Initializable {
    @FXML
    private AnchorPane homePane;
    @FXML
    private AnchorPane reservationsPane;
    @FXML
    private AnchorPane historyPane;
    @FXML
    private AnchorPane seaViewPane;
    @FXML
    private AnchorPane cityViewPane;
    @FXML
    private TableView<Room> seaViewRoomTable;

    @FXML
    private TableColumn<Room, Integer> seaViewRoomNumber_col;
    @FXML
    private TableColumn<Room, Integer> seaViewFloorNumber_col;
    @FXML
    private TableColumn<Room, String> seaViewRoomType_col;
    @FXML
    private TableColumn<Room, Integer> seaViewBedNumber_col;
    @FXML
    private TableColumn<Room, Double> seaViewPrice_col;
    @FXML
    private TableColumn<Room, Integer> seaViewCapacity_col;
    @FXML
    private TableColumn<Room, String> seaViewAvailable_col;

    @FXML
    private void handleHome(){
        homePane.setVisible(true);
        reservationsPane.setVisible(false);
        historyPane.setVisible(false);
        seaViewPane.setVisible(false);
        cityViewPane.setVisible(false);
    }
    @FXML
    private void handleReservations(){
        homePane.setVisible(false);
        reservationsPane.setVisible(true);
        historyPane.setVisible(false);
        seaViewPane.setVisible(false);
        cityViewPane.setVisible(false);
    }
    @FXML
    private void handleHistory(){
        homePane.setVisible(false);
        reservationsPane.setVisible(false);
        historyPane.setVisible(true);
        seaViewPane.setVisible(false);
        cityViewPane.setVisible(false);
    }

    @FXML
    private void handleLogOut(ActionEvent ae) {
        Navigator.navigate(ae,Navigator.LOGIN_PAGE);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void handleCheckSeaView(MouseEvent me){
        homePane.setVisible(false);
        reservationsPane.setVisible(false);
        historyPane.setVisible(false);
        seaViewPane.setVisible(true);
        cityViewPane.setVisible(false);
    }
    @FXML
    public void handleCheckCityView(MouseEvent me){
        homePane.setVisible(false);
        reservationsPane.setVisible(false);
        historyPane.setVisible(false);
        seaViewPane.setVisible(false);
        cityViewPane.setVisible(true);
    }
    @FXML
    public void handleProceeding(MouseEvent me) {
        Navigator.navigate(me, Navigator.PROCEEDING_PAGE);
    }

    private void showSeaViewRooms() {
        ObservableList<Room> listData = UserRepository.listSeaViewRooms();

        seaViewRoomNumber_col.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        seaViewFloorNumber_col.setCellValueFactory(new PropertyValueFactory<>("floorNumber"));
        seaViewRoomType_col.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        seaViewBedNumber_col.setCellValueFactory(new PropertyValueFactory<>("bedNumber"));
        seaViewPrice_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        seaViewCapacity_col.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        seaViewAvailable_col.setCellValueFactory(cellData -> {
            Room room = cellData.getValue();
            String availability = room.isAvailable() ? "Available" : "Unavailable";
            return new SimpleStringProperty(availability);
        });

        seaViewRoomTable.setItems(listData);
    }
}
