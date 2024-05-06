package controllers;

import App.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class UserInterface implements Initializable {
    @FXML
    private  Label firstName= new Label();
    @FXML
    private  Label lastName= new Label();
    @FXML
    private void handleLogOut(ActionEvent ae) {
        Navigator.navigate(ae,Navigator.LOGIN_PAGE);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
