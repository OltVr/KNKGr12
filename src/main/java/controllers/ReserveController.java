package controllers;

import App.Navigator;
import App.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;

import java.util.Locale;
import java.util.ResourceBundle;

public class ReserveController {
    private ResourceBundle bundle;
    @FXML
    private Text rsrvRoom;
    @FXML
    private Text totalPrice;
    @FXML
    private DatePicker checkInDate;
    @FXML
    private DatePicker checkOutDate;


    @FXML
    private void initialize(){
        Locale locale = Locale.getDefault();

        if (locale.getLanguage().equals("sq")){
            bundle= ResourceBundle.getBundle("translations.content", locale);
        }
        else {
            bundle = ResourceBundle.getBundle("translations.content_en", locale);

            rsrvRoom.setText(String.valueOf(SessionManager.getSelectedRoom().getRoomNumber()));
        }
    }
    @FXML
    private void handleChangeLanguage(ActionEvent ae) {

        if (Navigator.locale.getLanguage().equals("en")){
            Navigator.changeLanguage(ae,"sq");
        }
        else if  (Navigator.locale.getLanguage().equals("sq")){
            Navigator.changeLanguage(ae,"en");
        }

    }

}
