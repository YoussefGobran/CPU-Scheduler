package com.example.cpu_scheduler;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class HelloApplication extends Application implements EventHandler<ActionEvent> {
    private List<Process> processList = new ArrayList<>();
    String scheduler = null;
    private int counter = 0;
    private Label schedulerLabel , timeLabel;
    Scene scene1, scene2;
    Rectangle rect ;
    double xCoordinate ;
    public void printProcessList() {
        System.out.println("Current List of Processes:");
        for (Process process : processList) {
            System.out.println(process.getArrival_time()+" "+process.getBurst_time()+" "+ process.getPriority());
            System.out.println();
        }
    }
    @Override
    public void start(Stage stage) throws IOException {
        //flags
        final int rectangleWidth = 60;
        final int rectangleHeight = 40;
        final int spacing = 10;
        final boolean[] priorityFlag = {false};
        // Value storing the type of Scheduler
        //set stage
        stage.setResizable(false);
        stage.setTitle("CPU SCHEDULER - ENG ASU");
        //setting pane
        GridPane pane = new GridPane(); //radio buttons holder
        pane.setHgap(8); // Set horizontal gap
        pane.setVgap(32); // Set vertical gap
        pane.setPadding(new Insets(4, 48, 48, 48));

        Pane pane2 = new Pane(); // tabel holder
        pane2.setPadding(new Insets(4, 48, 10, 48));

        GridPane grid = new GridPane(); //text fields and add button holder
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Pane pane3 = new Pane(); // schedular button holder
        pane3.setPadding(new Insets(4, 48, 10, 48));

        //text set
        Text t = new Text (10, 20, "choose scheduler");
        pane.add(t,0,1);

        //Text Fields
        //Creating a GridPane container


        final TextField pName = new TextField();
        pName.setPromptText("process Name");
        pName.setPrefColumnCount(15);
        GridPane.setConstraints(pName, 5, 5);

        final TextField pArrivalTime = new TextField();
        pArrivalTime.setPromptText("Arrival Time");
        pArrivalTime.setPrefColumnCount(15);
        GridPane.setConstraints(pArrivalTime, 9, 5);

        final TextField pBurstTime = new TextField();
        pBurstTime.setPromptText("Burst Time");
        pBurstTime.setPrefColumnCount(15);
        GridPane.setConstraints(pBurstTime, 7, 5);

        final TextField pPriority = new TextField();
        pPriority.setPromptText("Priority");
        pPriority.setPrefColumnCount(15);
        GridPane.setConstraints(pPriority, 10, 5);

        // Add process button
        Button addButton = new Button("Add Process");
        GridPane.setConstraints(addButton, 5, 7);

        Button scheduleButton = new Button("Schedule Processes");
        scheduleButton.setLayoutX(168);
        //GridPane.setConstraints(scheduleButton, 9, 8);

        //Radio buttons setting
        ToggleGroup group = new ToggleGroup();
        RadioButton FCFS_button = new RadioButton("FCFS");
        FCFS_button.setToggleGroup(group);
        pane.add(FCFS_button,0,2);
        RadioButton SJF_button = new RadioButton("SJF");
        SJF_button.setToggleGroup(group);
        pane.add(SJF_button,1,2);
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

        //Table setting
        TableView <Process> table= new TableView<Process>();
        table.setPrefHeight(300);
        //set table column
        TableColumn<Process, String> processName = new TableColumn<>("processName");
        processName.setCellValueFactory(new PropertyValueFactory<>("process_name"));

        TableColumn<Process, Integer> burstTime = new TableColumn<>("Burst Time");
        burstTime.setCellValueFactory(new PropertyValueFactory<>("burst_time"));

        TableColumn<Process, Integer> arrivalTime = new TableColumn<>("Arrival Time");
        arrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrival_time"));

        TableColumn<Process, Integer> priority = new TableColumn<>("PRIORITY");
        priority.setCellValueFactory(new PropertyValueFactory<>("priority"));

        //////////////////////////////////////////////////////////////////Tabel/////////////////////////////////////////////////////
// Set column alignment and resizing
        processName.setSortable(false); // Example: Disable sorting for Process Name column
        burstTime.setSortable(false);    // Enable sorting for Burst Time column
        arrivalTime.setSortable(false);  // Enable sorting for Arrival Time column
        priority.setSortable(false);      // Enable sorting for Priority column

// Set column resizing
        processName.setResizable(false); // Example: Disable resizing for Process Name column
        burstTime.setResizable(false);    // Enable resizing for Burst Time column
        arrivalTime.setResizable(false);  // Enable resizing for Arrival Time column
       priority.setResizable(false);      // Enable resizing for Priority column
        //////////////////////////////////////////////////////////////////Tabel/////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////   Event Handlers  //////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Radio buttons event handling

        FCFS_button.setOnAction((e)->{
            table.setLayoutX(100);
            table.getColumns().addAll(processName, burstTime, arrivalTime);
            pane2.getChildren().add(table);
            grid.getChildren().add(pName);
            grid.getChildren().add(pBurstTime);
            grid.getChildren().add(pArrivalTime);
            grid.getChildren().add(addButton);
            pane3.getChildren().add(scheduleButton);
            updateScheduler("FCFS");
        });
        SJF_button.setOnAction((e)->{
            table.setLayoutX(100);
            table.getColumns().addAll(processName, burstTime, arrivalTime);
            pane2.getChildren().add(table);
            grid.getChildren().add(pName);
            grid.getChildren().add(pBurstTime);
            grid.getChildren().add(pArrivalTime);
            grid.getChildren().add(addButton);
            pane3.getChildren().add(scheduleButton);
            updateScheduler("SJF");


        });
        SRTF_button.setOnAction((e)->{
            table.setLayoutX(100);
            table.getColumns().addAll(processName, burstTime, arrivalTime);
            pane2.getChildren().add(table);
            grid.getChildren().add(pName);
            grid.getChildren().add(pBurstTime);
            grid.getChildren().add(pArrivalTime);
            grid.getChildren().add(addButton);
            pane3.getChildren().add(scheduleButton);
            updateScheduler("SRTF");

        });
        PP_button.setOnAction((e)->{
            table.setLayoutX(60);
            table.getColumns().addAll(processName, burstTime, arrivalTime,priority);
            pane2.getChildren().add(table);
            grid.getChildren().add(pName);
            grid.getChildren().add(pBurstTime);
            grid.getChildren().add(pArrivalTime);
            grid.getChildren().add(pPriority);
            grid.getChildren().add(addButton);
            pane3.getChildren().add(scheduleButton);
            priorityFlag[0] = true;
            updateScheduler("PP");

        });
        PNP_button.setOnAction((e)->{
            table.setLayoutX(60);
            table.getColumns().addAll(processName, burstTime, arrivalTime,priority);
            pane2.getChildren().add(table);
            grid.getChildren().add(pName);
            grid.getChildren().add(pBurstTime);
            grid.getChildren().add(pArrivalTime);
            grid.getChildren().add(pPriority);
            grid.getChildren().add(addButton);
            pane3.getChildren().add(scheduleButton);
            priorityFlag[0]=true;
            updateScheduler("PNP");

        });
        RR_button.setOnAction((e)->{
            table.setLayoutX(100);
            table.getColumns().addAll(processName, burstTime, arrivalTime);
            pane2.getChildren().add(table);
            grid.getChildren().add(pName);
            grid.getChildren().add(pBurstTime);
            grid.getChildren().add(pArrivalTime);
            grid.getChildren().add(addButton);
            pane3.getChildren().add(scheduleButton);
            updateScheduler("RR");

        });

        ;
// Add button Handler
        addButton.setOnAction(e -> {
            // Retrieve input values from text fields
            try {

                String inputName = pName.getText() ;
                int inputArrivalTime = Integer.parseInt(pArrivalTime.getText());
                int inputBurstTime = Integer.parseInt(pBurstTime.getText());

                Process process ;
              if(priorityFlag[0])
              {
                   // If priority is being used
                  int inputPriority = Integer.parseInt(pPriority.getText());
                  ObservableList<Process> data = FXCollections.observableArrayList(
                          process = new Process(inputName, inputArrivalTime, inputBurstTime, inputPriority)
                  );
              }
              else
              {
                  ObservableList<Process> data = FXCollections.observableArrayList(
                          process = new Process(inputName, inputArrivalTime, inputBurstTime)
                  );

              }
                table.getItems().add(process);
                // Clear the TextField contents after adding the process
                pName.clear();
                pArrivalTime.clear();
                pBurstTime.clear();
                pPriority.clear(); // Clear this if using priority

                processList.add(process);


            } catch (NumberFormatException ex) {
                // Handle the case where one or more inputs are not valid integers
                System.out.println("Please enter valid integers for all fields");
            }

        });




        //scene 1
// Create a VBox to hold your GridPane and other components
        VBox vbox = new VBox();
// Add the GridPane to the VBox
        vbox.getChildren().addAll(pane,pane2,grid,pane3);
// Create the scene with the VBox
         scene1 = new Scene(vbox, 450, 600);

        //  scene 2
        Label label2 = new Label("Output");
        Button button2 = new Button("Go to scene 1");
         schedulerLabel = new Label("Default Scheduler");
        schedulerLabel.setAlignment(Pos.TOP_RIGHT);

        // Label that updates every second
        timeLabel = new Label("Counter: 0");

        // Timeline for updating the label
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            updateLabel(timeLabel);
            drawRectangle();
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        VBox layout2 = new VBox(20);
        layout2.setAlignment(Pos.CENTER);
        layout2.getChildren().addAll(label2, button2, schedulerLabel, timeLabel);
        scene2 = new Scene(layout2, 500, 450);

            //button actions
        scheduleButton.setOnAction(e -> {
            part2(processList, scheduler);
             stage.setScene(scene2);
        });
        button2.setOnAction(e-> stage.setScene(scene1));

// Set the initial scene to the stage
        stage.setScene(scene1);
        stage.show();

    }
    // Method to update the private string and label
    private void updateScheduler(String schedulerName) {
        scheduler = schedulerName;
        schedulerLabel.setText("Current Scheduler: " + scheduler);
    }

void part2 (List<Process> processList, String Scheduler) {
   // Print processes for debugging
    printProcessList();
}
    // Function to update the label
    private void updateLabel(Label label) {
        counter++;
        label.setText("Counter: " + counter);
    }
    // Method to draw rectangles
    private void drawRectangle() {
        final int rectangleWidth = 60;
        final int rectangleHeight = 40;

        // Calculate the x-coordinate based on the counter and rectangle width
         xCoordinate  = xCoordinate+ rectangleWidth;

        // Create a new rectangle with the calculated x-coordinate
        rect = new Rectangle(50, xCoordinate, rectangleWidth, rectangleHeight);
        rect.setFill(Color.DARKCYAN);

        // Add the rectangle to the layout of scene 2
        VBox layout2 = (VBox) scene2.getRoot();
        layout2.getChildren().add(rect);
    }

    public static void main(String[] args) {

        launch();
    }


    @Override
    public void handle(ActionEvent actionEvent) {

    }
}