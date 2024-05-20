package controllers;

import App.Navigator;
import App.SessionManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Room;
import model.dto.CreateReservationDto;
import service.UserService;

import java.time.LocalDate;
import java.util.Date;
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


    @FXML
    private void resetCheckOutDate(){
        try {
            LocalDate date = checkOutDate.getValue();
            if (date.isEqual(checkInDate.getValue()) || date.isBefore(checkInDate.getValue())) {
                checkOutDate.setValue(null);
                lblTotalPrice.setText("0.00");
            }
            else {
                double totalprice = UserService.totalPrice(checkInDate.getValue(), date, SessionManager.getPrice());
                lblTotalPrice.setText(String.valueOf(totalprice));
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
        else if(endDate.isBefore(startDate)){
            lblTotalPrice.setText("0.00");
        }
    }

    @FXML
    private void handleReserve(ActionEvent ae){
        CreateReservationDto reservation= new CreateReservationDto(
                SessionManager.getUserEmail(),
                SessionManager.getSelectedRoomNumber(),
                checkInDate.getValue(),
                checkOutDate.getValue()
        );
        if(!UserService.makeReservation(reservation)){
            System.out.println("[RESERVATION] Failed to reserve! Insert a popup");
        }
        else {
            System.out.println("[RESERVATION] Reservation is complete! Insert a popup");
            Navigator.navigate(ae,Navigator.HOME_PAGE);
        }
    }


    public void handleProceeding(MouseEvent me) {
        Navigator.navigate(me, Navigator.PROCEEDING_PAGE);
    }
}