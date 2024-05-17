package controllers;

import App.Navigator;
import App.SessionManager;
import Repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.dto.CreateReservationDto;

import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReserveController {
    private ResourceBundle bundle;

    @FXML
    private Label roomNumberLabel;
    @FXML
    private DatePicker checkInDatePicker;
    @FXML
    private DatePicker checkOutDatePicker;


    @FXML
    private void initialize(){
        Locale locale = Locale.getDefault();

        if (locale.getLanguage().equals("sq")){
            bundle= ResourceBundle.getBundle("translations.content", locale);
        }
        else {
            bundle = ResourceBundle.getBundle("translations.content_en", locale);
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
