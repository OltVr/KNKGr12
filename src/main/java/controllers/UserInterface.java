package controllers;

import App.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
}
