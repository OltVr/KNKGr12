package controllers;

import App.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class AdminDashboard {
    @FXML
    private AnchorPane dashboardPane;

    @FXML
    private AnchorPane roomPane;

    @FXML
    private void handleLogout(MouseEvent me) {
        Navigator.navigate(me,Navigator.LOGIN_PAGE);
    }

    @FXML
    private void handleDashboard(ActionEvent ae){
        dashboardPane.setVisible(true);
        roomPane.setVisible(false);
    }

    @FXML
    private void handleRooms(ActionEvent ae){
        dashboardPane.setVisible(false);
        roomPane.setVisible(true);
    }
}
