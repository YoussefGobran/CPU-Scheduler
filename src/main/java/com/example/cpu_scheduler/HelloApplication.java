package com.example.cpu_scheduler;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application implements EventHandler<ActionEvent> {
    private List<Process> processList = new ArrayList<>();
    public void printProcessList() {
        System.out.println("Current List of Processes:");
        for (Process process : processList) {
            System.out.println(process.getArrival_time()+" "+process.getBurst_time()+" "+ process.getPriority());
            System.out.println();
        }
    }
    @Override
    public void start(Stage stage) throws IOException {
        //set stage
        stage.setResizable(true);

        //setting pane
        GridPane pane = new GridPane();
        pane.setHgap(8); // Set horizontal gap
        pane.setVgap(32); // Set vertical gap
        pane.setPadding(new Insets(4, 48, 48, 48));

        Pane pane2 = new Pane();

        pane2.setPadding(new Insets(4, 48, 48, 48));


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
        PNP_button.setToggleGroup(group);
        pane.add(PNP_button,2,3);
        RadioButton RR_button = new RadioButton("ROUND_ROBIN");
        RR_button.setToggleGroup(group);
        pane.add(RR_button,0,3);
//// Add event handlers to PP and PNP buttons
//
//        PP_button.setOnAction(e -> {
//            if (PP_button.isSelected()) {
//                PNP_button.setSelected(false); // Unselect PNP if PP is selected
//            }
//        });
//
//        PNP_button.setOnAction(e -> {
//            if (PNP_button.isSelected()) {
//                PP_button.setSelected(false); // Unselect PP if PNP is selected
//            }
//        });

        //Table setting
        TableView <Process> table= new TableView<Process>();
        table.setPrefHeight(300);
        //table.setLayoutX(60);

        TableColumn processName = new TableColumn("Process Name");
        TableColumn burstTime = new TableColumn("Burst Time");
        TableColumn arrivalTime = new TableColumn("Arrival Time");
        TableColumn priority = new TableColumn("PRIORITY");

// Clear existing columns before adding new ones
        table.getColumns().clear();
        // Define a class to hold the selected algorithm
        class SelectedAlgorithm {
            String algorithm = "";

            public void setAlgorithm(String algorithm) {
                this.algorithm = algorithm;
            }

            public String getAlgorithm() {
                return algorithm;
            }
        }

// Create an instance of the class
        SelectedAlgorithm selectedAlgorithm = new SelectedAlgorithm();

// Set up event handlers for radio buttons to update the selectedAlgorithm object
        FCFS_button.setOnAction((e) -> {
            selectedAlgorithm.setAlgorithm("FCFS");
        });

        SJS_button.setOnAction((e) -> {
            selectedAlgorithm.setAlgorithm("SJS");

        });

// Similar event handlers for other radio buttons...

        PP_button.setOnAction((e) -> {
            selectedAlgorithm.setAlgorithm("PP");
            // Additional actions if needed
        });

        PNP_button.setOnAction((e) -> {
            selectedAlgorithm.setAlgorithm("PNP");
            // Additional actions if needed
        });

        RR_button.setOnAction((e) -> {
            selectedAlgorithm.setAlgorithm("RR");
            // Additional actions if needed
        });

// Add columns based on the selected scheduling algorithm
//        if (selectedAlgorithm.getAlgorithm().equals("FCFS") || selectedAlgorithm.getAlgorithm().equals("SJS") || selectedAlgorithm.getAlgorithm().equals("SRTF") || selectedAlgorithm.getAlgorithm().equals("RR")) {
//            table.getColumns().addAll(processName, burstTime, arrivalTime);
//        } else if (selectedAlgorithm.getAlgorithm().equals("PP") || selectedAlgorithm.getAlgorithm().equals("PNP")) {
//            table.getColumns().addAll(processName, burstTime, arrivalTime, priority);
//        }

// Set column alignment and resizing
        processName.setSortable(false); // Example: Disable sorting for Process Name column
        burstTime.setSortable(true);    // Enable sorting for Burst Time column
        arrivalTime.setSortable(true);  // Enable sorting for Arrival Time column
        priority.setSortable(true);      // Enable sorting for Priority column

// Set column resizing
        processName.setResizable(false); // Example: Disable resizing for Process Name column
        burstTime.setResizable(true);    // Enable resizing for Burst Time column
        arrivalTime.setResizable(true);  // Enable resizing for Arrival Time column
        priority.setResizable(true);      // Enable resizing for Priority column
        //Radio buttons event handling
        FCFS_button.setOnAction((e)->{
            table.setLayoutX(90);
            table.getColumns().addAll(processName, burstTime, arrivalTime);
            pane2.getChildren().add(table);
        });
        SJS_button.setOnAction((e)->{
            table.setLayoutX(90);
            table.getColumns().addAll(processName, burstTime, arrivalTime);
            pane2.getChildren().add(table);
        });
        SRTF_button.setOnAction((e)->{
            table.setLayoutX(90);
            table.getColumns().addAll(processName, burstTime, arrivalTime);
            pane2.getChildren().add(table);
        });
        PP_button.setOnAction((e)->{
            table.setLayoutX(60);
            table.getColumns().addAll(processName, burstTime, arrivalTime,priority);
            pane2.getChildren().add(table);
        });
        PNP_button.setOnAction((e)->{
            table.setLayoutX(60);
            table.getColumns().addAll(processName, burstTime, arrivalTime,priority);
            pane2.getChildren().add(table);
        });
        RR_button.setOnAction((e)->{
            table.setLayoutX(90);
            table.getColumns().addAll(processName, burstTime, arrivalTime);
            pane2.getChildren().add(table);
        });
//Creating a GridPane container
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        final TextField pName = new TextField();
        pName.setPromptText("Enter process Name/Number");
        pName.setPrefColumnCount(15);
        GridPane.setConstraints(pName, 5, 5);
        grid.getChildren().add(pName);

        final TextField pArrivalTime = new TextField();
        pArrivalTime.setPromptText("Enter process Arrival Time");
        pArrivalTime.setPrefColumnCount(15);
        GridPane.setConstraints(pArrivalTime, 7, 5);
        grid.getChildren().add(pArrivalTime);

        final TextField pBurstTime = new TextField();
        pBurstTime.setPromptText("Enter process Burst Time");
        pBurstTime.setPrefColumnCount(15);
        GridPane.setConstraints(pBurstTime, 9, 5);
        grid.getChildren().add(pBurstTime);
        final TextField pPriority = new TextField();
        pPriority.setPromptText("Enter process Priority");
        pPriority.setPrefColumnCount(15);
        GridPane.setConstraints(pPriority, 11, 5);
        grid.getChildren().add(pPriority);

        Button addButton = new Button("Add Process");
        addButton.setOnAction(e -> {
            // Retrieve input values from text fields
            try {
//                String inputName = pName.getText() ;
                int inputArrivalTime = Integer.parseInt(pArrivalTime.getText());
                int inputBurstTime = Integer.parseInt(pBurstTime.getText());
                int inputPriority = Integer.parseInt(pPriority.getText()); // If priority is being used

                // Create a new Process object
                Process process = new Process(inputArrivalTime, inputBurstTime, inputPriority); // Use the priority constructor if needed
                processList.add(process);

                // Clear the TextField contents after adding the process
                pName.clear();
                pArrivalTime.clear();
                pBurstTime.clear();
                pPriority.clear(); // Clear this if using priority

//              Print processes for debugging
//                printProcessList();
            } catch (NumberFormatException ex) {
                // Handle the case where one or more inputs are not valid integers
                System.out.println("Please enter valid integers for all fields");
            }

        });

// Create a VBox to hold your GridPane and other components
        VBox vbox = new VBox();

// Add the GridPane to the VBox
        vbox.getChildren().addAll(pane,pane2,addButton,grid);

// Create the scene with the VBox
        Scene scene = new Scene(vbox, 400, 400);

// Set the scene to the stage

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