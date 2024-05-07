package controllers;

import App.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.dto.LoginUserDto;
import service.UserService;

public class LoginController {
    @FXML
    private TextField txtLoginEmail;
    @FXML
    private PasswordField pwdLoginPassword;

    public void initialize() {
        // Set up event listeners for the text fields
        txtLoginEmail.setOnKeyPressed(this::handleKeyLogin);
        pwdLoginPassword.setOnKeyPressed(this::handleKeyLogin);
    }
    @FXML
    private void handleLogin(ActionEvent ae){
        LoginUserDto loginUserData = new LoginUserDto(
                this.txtLoginEmail.getText(),
                this.pwdLoginPassword.getText()
        );
        boolean isLogin= UserService.login(loginUserData);

        if (!isLogin){
            showAlert("Invalid Login", "Wrong email or password, please try again!");
        }
        else{
            Navigator.navigate(ae,Navigator.HOME_PAGE);
        }
    }
    @FXML
    private void handleKeyLogin(KeyEvent ke) {
        if (ke.getCode() == KeyCode.ENTER) {
            LoginUserDto loginUserData = new LoginUserDto(
                    this.txtLoginEmail.getText(),
                    this.pwdLoginPassword.getText()
            );
            boolean isLogin = UserService.login(loginUserData);

            if (!isLogin) {
                showAlert("Invalid Login", "Wrong email or password, please try again!");
            } else {
                Navigator.navigate(ke, Navigator.HOME_PAGE);
            }
        }
    }
        @FXML
        private void handleCreateAccount (MouseEvent me){
            Navigator.navigate(me, Navigator.CREATE_ACCOUNT_PAGE);
        }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
