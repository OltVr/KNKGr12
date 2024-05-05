package controllers;

import App.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.dto.UserDto;
import service.UserService;

public class SignupController {
    @FXML
    private TextField txtSignUpName;
    @FXML
    private TextField txtSignUpLastName;
    @FXML
    private TextField txtSignUpEmail;
    @FXML
    private PasswordField pwdSignUpPassword;
    @FXML
    private PasswordField pwdSignUpConfirmPassword;

    @FXML
    private void handleSignUp(ActionEvent ae) {
        UserDto userSignUpData = new UserDto(
                this.txtSignUpName.getText(),
                this.txtSignUpLastName.getText(),
                this.txtSignUpEmail.getText(),
                this.pwdSignUpPassword.getText(),
                this.pwdSignUpConfirmPassword.getText()
        );
        boolean response = UserService.signUp(userSignUpData);

        if (response) {
            Navigator.navigate(ae, Navigator.LOGIN_PAGE);
        }
    }
}
