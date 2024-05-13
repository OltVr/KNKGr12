package App;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {
    public final static String LOGIN_PAGE = "login_form.fxml";
    public final static String HOME_PAGE = "user_home.fxml";
    public final static String CREATE_ACCOUNT_PAGE = "signup_form.fxml";
    public final static String LOGIN_FAIL_ALERT = "login_failed.fxml";
    public final static String ADMIN_DASHBOARD = "admin_dashboard.fxml";
    public final static String PROCEEDING_PAGE = "proceeding.fxml";


    public static void navigate(Stage stage, String page){
        FXMLLoader loader = new FXMLLoader(
                Navigator.class.getResource(page)
        );

        try{
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    public static void navigate(Event event, String page) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        navigate(stage, page);
    }
}