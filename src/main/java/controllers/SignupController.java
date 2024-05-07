package controllers;

import App.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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

    // Regex patterns for email and password validation
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&*?+=!])(?=\\S+$).{8,}$";

    @FXML
    private void handleSignUp(ActionEvent ae) {
        String email = txtSignUpEmail.getText();
        String password = pwdSignUpPassword.getText();

        // Validate email
        if (!email.matches(EMAIL_REGEX)) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return;
        }

        // Validate password
        if (!password.matches(PASSWORD_REGEX)) {

            showAlert("Invalid Password", "Password requires at least 1 digit, lowercase & uppercase letter, special character, and minimum length of 8 characters.");
            return;
        }

        // Other signup logic
        UserDto userSignUpData = new UserDto(
                this.txtSignUpName.getText(),
                this.txtSignUpLastName.getText(),
                email,
                password,
                this.pwdSignUpConfirmPassword.getText()
        );

        boolean response = UserService.signUp(userSignUpData);

        if (response) {
            Navigator.navigate(ae, Navigator.LOGIN_PAGE);
        }
    }

    @FXML
    private void handleHaveAnAccount(MouseEvent me){
        Navigator.navigate(me, Navigator.LOGIN_PAGE);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
