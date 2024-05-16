package controllers;

import App.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.util.Locale;
import java.util.ResourceBundle;

public class ProceedingController {
    private ResourceBundle bundle;
    @FXML
    private DatePicker checkInDate;
    @FXML
    private DatePicker checkOutDate;


    @FXML
    private void initialize(){
        Locale locale = Locale.getDefault();

        if (locale.getLanguage().equals("sq")){
            System.out.println("[GJUHA] Shqip");
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
            System.out.println("[CHANGE] ALBANIAN");
        }
        else if  (Navigator.locale.getLanguage().equals("sq")){
            Navigator.changeLanguage(ae,"en");
            System.out.println("[CHANGE] ENGLISH");
        }

    }

    private void handleReserve(ActionEvent ae){

    }
}
