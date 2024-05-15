module com.example.knkgr {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    exports  App;
    opens controllers to javafx.fxml;
    opens model to javafx.base;
    opens model.dto to javafx.base;

    opens com.example.knkgr13 to javafx.fxml;
    exports com.example.knkgr13;
    opens controllers.User to javafx.fxml;
    opens controllers.Admin to javafx.fxml;


}