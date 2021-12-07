module com.example.it {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.it to javafx.fxml;
    exports com.example.it;
    exports com.example.it.model;
    opens com.example.it.model to javafx.fxml;
}