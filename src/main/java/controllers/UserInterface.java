package controllers;

import App.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    private void handleHome(){
        homePane.setVisible(true);
        reservationsPane.setVisible(false);
        historyPane.setVisible(false);
    }
    @FXML
    private void handleReservations(){
        homePane.setVisible(false);
        reservationsPane.setVisible(true);
        historyPane.setVisible(false);

    }
    @FXML
    private void handleHistory(){
        homePane.setVisible(false);
        reservationsPane.setVisible(false);
        historyPane.setVisible(true);
    }

    @FXML
    private void handleLogOut(ActionEvent ae) {
        Navigator.navigate(ae,Navigator.LOGIN_PAGE);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
