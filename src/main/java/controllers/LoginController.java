package controllers;

import App.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.dto.LoginUserDto;
import service.UserService;

import java.util.logging.Handler;

public class LoginController {
    @FXML
    private TextField txtLoginEmail;
    @FXML
    private PasswordField pwdLoginPassword;
    @FXML
    private void handleLogin(ActionEvent ae){
        LoginUserDto loginUserData = new LoginUserDto(
                this.txtLoginEmail.getText(),
                this.pwdLoginPassword.getText()
        );
        boolean isLogin= UserService.login(loginUserData);

        if (!isLogin){
            System.out.println("Wrong login");
        }
        else{
            Navigator.navigate(ae,Navigator.HOME_PAGE);
        }




    }
    @FXML
    private void handleCreateAccount(MouseEvent me){
        Navigator.navigate(me,Navigator.CREATE_ACCOUNT_PAGE);
    }

}
