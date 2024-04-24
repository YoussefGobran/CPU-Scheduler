package com.example.cpu_scheduler;

import com.example.cpu_scheduler.Scheduler.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class HelloApplication extends Application implements EventHandler<ActionEvent> {// Initial x position for the first rectangle
    private List<Process> processList = new ArrayList<>();
    String scheduler_type = null;
    Scheduler schedulerInstance;
    @Override
    public void start(Stage stage) throws IOException {
        //flags
        final boolean[] priorityFlag = {false};
        //set stage
        stage.setResizable(true);
        stage.setTitle("CPU SCHEDULER - ENG ASU");
        //setting pane
        GridPane pane = new GridPane(); //radio buttons holder
        pane.setHgap(8); // Set horizontal gap
        pane.setVgap(32); // Set vertical gap
        pane.setPadding(new Insets(4, 48, 48, 48));

        Pane pane2 = new Pane(); // table holder
        pane2.setPadding(new Insets(4, 48, 10, 48));

        GridPane grid = new GridPane(); //text fields and add button holder
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Pane pane3 = new Pane(); // scheduler button holder
        pane3.setPadding(new Insets(4, 48, 10, 48));

        //text set
        Text t = new Text(10, 20, "choose scheduler");
        pane.add(t, 0, 1);

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

        Button liveScheduling = new Button("Live Schedule");
        liveScheduling.setLayoutX(80);

        Button nonLiveScheduling = new Button("Non-Live Schedule");
        nonLiveScheduling.setLayoutX(200);
        //GridPane.setConstraints(scheduleButton, 9, 8);


        //Radio buttons setting
        ToggleGroup group = new ToggleGroup();
        RadioButton FCFS_button = new RadioButton("FCFS");
        FCFS_button.setToggleGroup(group);
        FCFS_button.setSelected(true);
        updateScheduler("FCFS");

        pane.add(FCFS_button, 0, 2);
        RadioButton SJF_button = new RadioButton("SJF");
        SJF_button.setToggleGroup(group);
        pane.add(SJF_button, 1, 2);
        RadioButton SRTF_button = new RadioButton("SRTF");
        SRTF_button.setToggleGroup(group);
        pane.add(SRTF_button, 1, 3);
        RadioButton PP_button = new RadioButton("PRIORITY-PREEMPTIVE");
        PP_button.setToggleGroup(group);
        pane.add(PP_button, 2, 2);
        RadioButton PNP_button = new RadioButton("PRIORITY-NON-PREEMPTIVE");
        PNP_button.setToggleGroup(group);
        pane.add(PNP_button, 2, 3);
        RadioButton RR_button = new RadioButton("ROUND_ROBIN");
        RR_button.setToggleGroup(group);
        pane.add(RR_button, 0, 3);

        //Table setting
        TableView<Process> table = new TableView<Process>();
        table.setPrefHeight(300);

        //set table column
        TableColumn<Process, String> processName = new TableColumn<>("processName");
        processName.setMinWidth(150);
        processName.setCellValueFactory(new PropertyValueFactory<>("process_name"));

        TableColumn<Process, Integer> remainingBurstTime = new TableColumn<>("Remaining Burst Time");
        remainingBurstTime.setMinWidth(150);
        remainingBurstTime.setCellValueFactory(new PropertyValueFactory<>("burst_time"));

        TableColumn<Process, Integer> originalBurstTime = new TableColumn<>("Original Burst Time");
        originalBurstTime.setMinWidth(150);
        originalBurstTime.setCellValueFactory(new PropertyValueFactory<>("original_burst_time"));

        TableColumn<Process, Integer> arrivalTime = new TableColumn<>("Arrival Time");
        arrivalTime.setMinWidth(150);
        arrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrival_time"));

        TableColumn<Process, Integer> priority = new TableColumn<>("PRIORITY");
        priority.setMinWidth(150);
        priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        table.setLayoutX(60);
        table.getColumns().addAll(processName, remainingBurstTime,originalBurstTime, arrivalTime, priority);
        pane2.getChildren().add(table);

        //text input fields
        grid.getChildren().add(pName);
        grid.getChildren().add(pBurstTime);
        grid.getChildren().add(pArrivalTime);
        grid.getChildren().add(pPriority);
        pPriority.setDisable(true);
        //buttons
        grid.getChildren().add(addButton);
        pane3.getChildren().addAll(liveScheduling, nonLiveScheduling);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////   Event Handlers  //////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Radio buttons event handling

        FCFS_button.setOnAction((e) -> {
            pPriority.setDisable(true);
            priorityFlag[0] = false;
            updateScheduler("FCFS");
        });
        SJF_button.setOnAction((e) -> {
            pPriority.setDisable(true);
            priorityFlag[0] = false;
            updateScheduler("SJF");
        });
        SRTF_button.setOnAction((e) -> {
            pPriority.setDisable(true);
            priorityFlag[0] = false;
            updateScheduler("SRTF");
        });
        PP_button.setOnAction((e) -> {
            pPriority.setDisable(false);
            priorityFlag[0] = true;
            updateScheduler("PP");

        });
        PNP_button.setOnAction((e) -> {
            pPriority.setDisable(false);
            priorityFlag[0] = true;
            updateScheduler("PNP");
        });
        RR_button.setOnAction((e) -> {
            pPriority.setDisable(true);
            priorityFlag[0] = false;
            updateScheduler("RR");
        });


// Add button Handler
        addButton.setOnAction(e -> {
            // Retrieve input values from text fields
            try {

                String inputName = pName.getText();
                int inputArrivalTime = Integer.parseInt(pArrivalTime.getText());
                int inputBurstTime = Integer.parseInt(pBurstTime.getText());
                Process process;
                if (priorityFlag[0]) {
                    // If priority is being used
                    int inputPriority = Integer.parseInt(pPriority.getText());
                    process = new Process(inputName, inputArrivalTime, inputBurstTime, inputPriority);
                } else {
                    process = new Process(inputName, inputArrivalTime, inputBurstTime);
                }
                table.getItems().add(process);
                // Clear the TextField contents after adding the process
                pName.clear();
                pArrivalTime.clear();
                pBurstTime.clear();
                pPriority.clear(); // Clear this if using priority
                if(schedulerInstance==null||!schedulerInstance.isValid){
                    processList.add(process);
                }else{
                    schedulerInstance.insertProcess(process);
                }



            } catch (NumberFormatException ex) {
                // Handle the case where one or more inputs are not valid integers
                System.out.println("Please enter valid integers for all fields");
            }

        });
        Callable<Integer> updateTable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                table.getItems().clear();
                if(schedulerInstance==null){
                    table.getItems().addAll(processList);
                }else{
                    table.getItems().addAll(schedulerInstance.processesList);
                }

                return 0;
            }
        };
        liveScheduling.setOnAction(e -> {
            schedulerInstance=generateSchedulerInstance(processList,scheduler_type);
            Stage gantDrawer = ScheduarDrawerStage.gantChartStageGenerate(
                    true,
                    scheduler_type,
                    schedulerInstance,

                    updateTable
            );
            gantDrawer.show();
        });
        nonLiveScheduling.setOnAction(e ->
        {
            schedulerInstance=generateSchedulerInstance(processList,scheduler_type);
            Stage gantDrawer = ScheduarDrawerStage.gantChartStageGenerate(
                    false,
                    scheduler_type,
                    schedulerInstance,
                    updateTable
            );
            gantDrawer.show();


        });

        //scene 1
// Create a VBox to hold your GridPane and other components
        VBox vbox = new VBox();
// Add the GridPane to the VBox
        vbox.getChildren().addAll(pane, pane2, grid, pane3);
// Create the scene with the VBox
        Scene scene1 = new Scene(vbox, 1000, 800);

        stage.setScene(scene1);
        stage.show();

    }

     public Scheduler generateSchedulerInstance(List<Process> processList, String scheduler_type) {

        // Instantiate the appropriate scheduler based on the input string
        switch (scheduler_type) {
            case "FCFS":
                return new FCFS(processList);
            case "RR":
                return new RR(processList);
            case "SJF":
                return new SJF(processList);
            case "SRTF":
                return new SRTF(processList);
            case "PP":
                return new PriorityPrimitiveSchedular(processList);
            case "PNP":
                return new PriorityNonPrimitive(processList);
            default:
                System.out.println("Invalid scheduler type: " + scheduler_type);
                break;
        }
        return null;
    }

    // Method to update the private string and label
    private void updateScheduler(String schedulerName) {
        scheduler_type = schedulerName;
    }


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}