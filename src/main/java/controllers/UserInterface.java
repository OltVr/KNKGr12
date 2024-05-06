package controllers;

import App.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class UserInterface {
    @FXML
    private  Label firstName= new Label();
    @FXML
    private  Label lastName= new Label();
    @FXML
    private void handleLogOut(ActionEvent ae) {
        Navigator.navigate(ae,Navigator.LOGIN_PAGE);


    }
    public  void updateLabels(String first, String last) {
        // Assuming you have labels named firstNameLabel and lastNameLabel
        firstName.setText(first);
        lastName.setText(last);}

}
