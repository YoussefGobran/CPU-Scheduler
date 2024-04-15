package com.example.cpu_scheduler;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application implements EventHandler<ActionEvent> {
    @Override
    public void start(Stage stage) throws IOException {
        //set stage
        stage.setResizable(true);
        //setting pain
        GridPane pane = new GridPane();
        pane.setHgap(8); // Set horizontal gap
        pane.setVgap(32); // Set vertical gap
        pane.setPadding(new Insets(4, 48, 48, 48));
        //text set
        Text t = new Text (10, 20, "choose scheduler");
        pane.add(t,0,1);
        //Radio buttons setting
        ToggleGroup group = new ToggleGroup();
        RadioButton FCFS_button = new RadioButton("FCFS");
        FCFS_button.setToggleGroup(group);
        pane.add(FCFS_button,0,2);
        RadioButton SJS_button = new RadioButton("SJS");
        SJS_button.setToggleGroup(group);
        pane.add(SJS_button,1,2);
        RadioButton SRTF_button = new RadioButton("SRTF");
        SRTF_button.setToggleGroup(group);
        pane.add(SRTF_button,1,3);
        RadioButton PP_button = new RadioButton("PRIORITY-PREEMEPTIVE");
        PP_button.setToggleGroup(group);
        pane.add(PP_button,2,2);
        RadioButton PNP_button = new RadioButton("PRIORITY-NONPREEMEPTIVE");
        PP_button.setToggleGroup(group);
        pane.add(PNP_button,2,3);
        RadioButton RR_button = new RadioButton("ROUND_ROBIN");
        RR_button.setToggleGroup(group);
        pane.add(RR_button,0,3);
        //Table setting
        TableView <Process> table= new TableView<Process>();
        TableColumn processName = new TableColumn("Process Name");
        TableColumn burstTime = new TableColumn("Burst Time");
        TableColumn arrivalTime = new TableColumn("Arrival Time");
        TableColumn priority = new TableColumn("PRIORITY");
        //Radio buttons event handling
        FCFS_button.setOnAction((e)->{
            table.getColumns().addAll(processName, burstTime, arrivalTime);
            pane.add(table,0,5);
        });
        SJS_button.setOnAction((e)->{
            table.getColumns().addAll(processName, burstTime, arrivalTime);
            pane.add(table,0,5);
        });
        SRTF_button.setOnAction((e)->{
            table.getColumns().addAll(processName, burstTime, arrivalTime);
            pane.add(table,0,5);
        });
        PP_button.setOnAction((e)->{
            table.getColumns().addAll(processName, burstTime, arrivalTime,priority);
            pane.add(table,0,5);
        });
        PNP_button.setOnAction((e)->{
            table.getColumns().addAll(processName, burstTime, arrivalTime,priority);
            pane.add(table,0,5);
        });
        RR_button.setOnAction((e)->{
            table.getColumns().addAll(processName, burstTime, arrivalTime);
            pane.add(table,0,5);
        });


//        TableColumn firstNameCol = new TableColumn("First Name");
//        firstNameCol.setMinWidth(100);
//
//        TableColumn lastNameCol = new TableColumn("Last Name");
//        lastNameCol.setMinWidth(100);
//        TableColumn emailCol = new TableColumn("Email");
//        emailCol.setMinWidth(200);
//        table.setItems(data);
//        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);


        //Radio buttons event handling
        //


//        Button ok = new Button("ok");
//        ok.setOnAction((e)->{
//            System.out.println("hello");
//        });
//        pane.add(ok,2,2);


        Scene scene = new Scene(pane);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }


    @Override
    public void handle(ActionEvent actionEvent) {

    }
}