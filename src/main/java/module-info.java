module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires freetts;

    opens com.example to javafx.fxml;
    exports com.example;
    opens com.example.main to javafx.fxml;
    exports com.example.main;
}
