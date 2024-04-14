module com.example.cpu_scheduler {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cpu_scheduler to javafx.fxml;
    exports com.example.cpu_scheduler;
}