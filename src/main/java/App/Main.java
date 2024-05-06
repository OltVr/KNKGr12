package App;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage){
        Navigator.navigate(stage, Navigator.CREATE_ACCOUNT_PAGE);
    }
}