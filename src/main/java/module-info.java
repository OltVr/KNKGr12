module com.example.knkgr {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.knkgr13 to javafx.fxml;
    exports com.example.knkgr13;
}