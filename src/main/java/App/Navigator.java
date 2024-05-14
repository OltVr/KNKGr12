package App;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Navigator {
    public final static String LOGIN_PAGE = "login_form.fxml";
    public final static String HOME_PAGE = "user_home.fxml";
    public final static String CREATE_ACCOUNT_PAGE = "signup_form.fxml";
    public final static String LOGIN_FAIL_ALERT = "login_failed.fxml";
    public final static String ADMIN_DASHBOARD = "admin_dashboard.fxml";
    public final static String PROCEEDING_PAGE = "proceeding.fxml";
    private static ResourceBundle bundle;
    public static Locale locale = Locale.getDefault();
    private static String currentPage;

    public static void navigate(Stage stage, String page){
        FXMLLoader loader = new FXMLLoader(
                Navigator.class.getResource(page)
        );

         // Get the user's current locale
// Load the appropriate resource bundle based on the locale

        if (locale.getLanguage().equals("sq")) {
            bundle = ResourceBundle.getBundle("translations.content_sq"); // Load Albanian resource bundle
        } else {
            bundle = ResourceBundle.getBundle("translations.content_en"); // Load English resource bundle by default
        }

        loader.setResources(bundle);
        try{
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
            currentPage=page;
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    public static void navigate(Event event, String page) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        navigate(stage, page);
    }

    public static void changeLanguage(Event e, String localeCode){
        locale = new Locale(localeCode);
        bundle = ResourceBundle.getBundle("translations.content", locale);
        System.out.println("[LOCALE LANG] "+ locale.getLanguage());
        Navigator.navigate(e,currentPage);
    }

    private static void refresh(){

    }
}