package controllers;

import App.Navigator;
import Repository.AdminRepository;
import Repository.UserRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Room;

import java.net.URL;
import java.util.Optional;
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
    private TableView<Room> seaTable;

    @FXML
    private TableColumn<Room, Integer> seaViewRoomNumber_col;
    @FXML
    private TableColumn<Room, Integer> seaViewFloorNumber_col;
    @FXML
    private TableColumn<Room, Integer> seaViewBedNumber_col;
    @FXML
    private TableColumn<Room, Double> seaViewPrice_col;
    @FXML
    private TableColumn<Room, Integer> seaViewCapacity_col;

    @FXML
    private TableView<Room> cityTable;
    @FXML
    private TableColumn<Room, Integer> cityViewRoomNumber_col;
    @FXML
    private TableColumn<Room, Integer> cityViewFloorNumber_col;
    @FXML
    private TableColumn<Room, Integer> cityViewBedNumber_col;
    @FXML
    private TableColumn<Room, Double> cityViewPrice_col;
    @FXML
    private TableColumn<Room, Integer> cityViewCapacity_col;


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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Log Out?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            Navigator.navigate(ae, Navigator.LOGIN_PAGE);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showSeaViewRooms();
        showCityViewRooms();
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
        seaViewBedNumber_col.setCellValueFactory(new PropertyValueFactory<>("bedNumber"));
        seaViewPrice_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        seaViewCapacity_col.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        seaTable.setItems(listData);
    }

    private void showCityViewRooms() {
        ObservableList<Room> listData = UserRepository.listCityViewRooms();

        cityViewRoomNumber_col.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        cityViewFloorNumber_col.setCellValueFactory(new PropertyValueFactory<>("floorNumber"));
        cityViewBedNumber_col.setCellValueFactory(new PropertyValueFactory<>("bedNumber"));
        cityViewPrice_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        cityViewCapacity_col.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        cityTable.setItems(listData);
    }

}
