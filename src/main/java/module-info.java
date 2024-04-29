module com.example.cpu_scheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.cpu_scheduler to javafx.fxml;
    exports com.example.cpu_scheduler;
}