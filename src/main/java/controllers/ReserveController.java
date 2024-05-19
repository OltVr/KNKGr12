package controllers;

import App.Navigator;
import App.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import service.UserService;

import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReserveController {
    private ResourceBundle bundle;
    @FXML
    private Text rsrvRoom;
    @FXML
    private Text lblTotalPrice;
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

        }
        rsrvRoom.setText(String.valueOf(SessionManager.getSelectedRoomNumber()));
        lblTotalPrice.setText("0.00");
        checkInDate.setValue(LocalDate.now());
        checkInDate.setDayCellFactory(picker -> new DateCell(){
            @Override
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                if(date.isBefore(LocalDate.now())){
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });
        checkOutDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                if (date.isBefore(checkInDate.getValue()) || date.isEqual(checkInDate.getValue())){
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb");
                }
            }
        });

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

    //FIXME: Think where the code(s) below should be put. Is it in the services?

    @FXML
    private void resetCheckOutDate(){
        try {
            LocalDate date = checkOutDate.getValue();
            if (date.isEqual(checkInDate.getValue()) || date.isBefore(checkInDate.getValue())) {
                checkOutDate.setValue(null);
            }
        }catch (NullPointerException ne){
            ne.getMessage();
        }
    }
    @FXML
    private void getPrices(){
        LocalDate startDate = checkInDate.getValue();
        LocalDate endDate = checkOutDate.getValue();
        if (checkOutDate!=null) {
            double totalprice = UserService.totalPrice(startDate, endDate, SessionManager.getPrice());
            lblTotalPrice.setText(String.valueOf(totalprice));
        }
        else{
            lblTotalPrice.setText("0.00");
        }
    }


}