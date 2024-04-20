package com.example.cpu_scheduler;

import com.example.cpu_scheduler.Scheduler.*;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application implements EventHandler<ActionEvent> {
    private Timeline timeline;
    double xPosition = 0; // Initial x position for the first rectangle
    private List<Process> processList = new ArrayList<>();
    private List<Process> newList = new ArrayList<>();
    private List<Process> output = new ArrayList<>();
    String scheduler = null;
    int currentTime=0;
    Scheduler schedulerInstance = null;
    private int counter = 0;
    private Label schedulerLabel , timeLabel;
    Scene scene1, scene2 , scene3;
    VBox rectangleWithLabel ;
    Rectangle rect ;
    double xCoordinate ;
    Boolean isLive = false ;
    Label avgTurn , avgWaiting ;
//    public void printProcessList() {
//        System.out.println("Current List of Processes:");
//        for (Process process : processList) {
//            System.out.println(process.getArrival_time()+" "+process.getBurst_time()+" "+ process.getPriority());
//            System.out.println();
//        }
//    }
    @Override
    public void start(Stage stage) throws IOException {
        //flags
        final int rectangleWidth = 60;
        final int rectangleHeight = 40;
//        final int spacing = 10;
        final boolean[] priorityFlag = {false};
        // Value storing the type of Scheduler
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

        Button liveScheduling  = new Button("Live Schedule");
        liveScheduling.setLayoutX(80);

        Button nonLiveScheduling  = new Button("Non-Live Schedule");
        nonLiveScheduling.setLayoutX(200);
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
        RadioButton PP_button = new RadioButton("PRIORITY-PREEMPTIVE");
        PP_button.setToggleGroup(group);
        pane.add(PP_button,2,2);
        RadioButton PNP_button = new RadioButton("PRIORITY-NON-PREEMPTIVE");
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

        ////////////////////////////////////////////////////////////////// Table /////////////////////////////////////////////////////
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
        //////////////////////////////////////////////////////////////////Table/////////////////////////////////////////////////////

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
            pane3.getChildren().addAll(liveScheduling,nonLiveScheduling);
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
            pane3.getChildren().addAll(liveScheduling,nonLiveScheduling);
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
            pane3.getChildren().addAll(liveScheduling,nonLiveScheduling);
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
            pane3.getChildren().addAll(liveScheduling,nonLiveScheduling);
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
            pane3.getChildren().addAll(liveScheduling,nonLiveScheduling);
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
            pane3.getChildren().addAll(liveScheduling,nonLiveScheduling);
            updateScheduler("RR");

        });


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
//        Label label2 = new Label("Output");
        Button switchToScene1Butt = new Button("Go to Main Page");
         schedulerLabel = new Label(scheduler);
        schedulerLabel.setAlignment(Pos.TOP_RIGHT);

        // Label that updates every second
        timeLabel = new Label("Counter: "+currentTime);
        Button drawGantChartBtn = new Button("Draw Gant Chart") ;

         avgTurn = new Label( ) ;
         avgWaiting = new Label( ) ;


        VBox vbox1 = new VBox(20);
        vbox1.setAlignment(Pos.TOP_LEFT);

        vbox1.getChildren().addAll( switchToScene1Butt, schedulerLabel, timeLabel,
                drawGantChartBtn,avgWaiting,avgTurn);
        HBox box = new HBox();
        liveScheduling.setOnAction(e -> {
            output = scheduleProcessing(processList,scheduler,currentTime);
            isLive= true ;
//            vbox1.getChildren().add(box) ;
             stage.setScene(scene2);
        });
        nonLiveScheduling.setOnAction(e->
        {
            output = scheduleProcessing(processList,scheduler,currentTime);
            stage.setScene(scene2);

        });

        final TextField ppName = new TextField();
        pName.setPromptText("process Name");
        pName.setPrefColumnCount(15);
        GridPane.setConstraints(pName, 5, 5);

        final TextField ppArrivalTime = new TextField();
        pArrivalTime.setPromptText("Arrival Time");
        pArrivalTime.setPrefColumnCount(15);
        GridPane.setConstraints(pArrivalTime, 9, 5);

        final TextField ppBurstTime = new TextField();
        pBurstTime.setPromptText("Burst Time");
        pBurstTime.setPrefColumnCount(15);
        GridPane.setConstraints(pBurstTime, 7, 5);

        final TextField ppPriority = new TextField();
        pPriority.setPromptText("Priority");
        pPriority.setPrefColumnCount(15);
        GridPane.setConstraints(pPriority, 10, 5);

        // Add process button
        Button addButton2 = new Button("Add Process");
        box.getChildren().addAll(ppName,ppArrivalTime,ppBurstTime,ppPriority,addButton2);
        addButton2.setOnAction(e-> {
            schedulerInstance.insertProcess(ppName.getText(),currentTime,Integer.parseInt(ppBurstTime.getText()),Integer.parseInt(ppPriority.getText()));
        });
//        Integer.parseInt(pArrivalTime.getText());
//        if(isLive){
//            vbox1.getChildren().add(box) ;
//        }
        scene2 = new Scene(vbox1, 500, 450);

            //button actions
        switchToScene1Butt.setOnAction(e-> stage.setScene(scene1));

        //scene 3
          Button switchToScene2 = new Button("Switch to scene 2") ;
        Button switchToScene1 = new Button("Switch to scene 1") ;

        // Button handlers for scene 3 buttons
        switchToScene1.setOnAction(e->{
            stage.setScene(scene1);
        });
        switchToScene2.setOnAction(e->{
            stage.setScene(scene2);
        });
        // Initialization of UI components should be outside the button action handler
        VBox scene3Vbox = new VBox(20);
        scene3Vbox.getChildren().addAll(switchToScene1, switchToScene2);
        scene3Vbox.setAlignment(Pos.TOP_CENTER);

        Scene scene3 = new Scene(scene3Vbox, 500, 500);


// Set up scene switching
        switchToScene1.setOnAction(e -> stage.setScene(scene1));
        switchToScene2.setOnAction(e -> stage.setScene(scene2));

// Button handler for drawing the Gantt chart
        drawGantChartBtn.setOnAction(e -> {
//            while(schedulerInstance.processEmpty())
            // Create a Timeline with a KeyFrame that runs every 3 seconds
            StackPane root = drawGantChart(output);
            root.setAlignment(Pos.CENTER_RIGHT);
            vbox1.getChildren().add(root) ;

//            scene3Vbox.getChildren().add(root); // Add new updated Gantt chart
//            stage.setScene(scene3); // Set scene after updating contents
        });

// Set this scene configuration once if not dynamic or needs refresh every time
        stage.setScene(scene3);

// Set the initial scene to the stage
        stage.setScene(scene1);
        stage.show();

    }
    // Method to update the private string and label
    private void updateScheduler(String schedulerName) {
        scheduler = schedulerName;
        schedulerLabel.setText("Current Scheduler: " + scheduler);
    }

//void part2 (List<Process> processList, String Scheduler) {
//   /*
// Print processes for debugging
//    printProcessList();
//*/
//}
    // Function to update the label
    private void updateLabel(Label label) {
        counter++;
        label.setText("Counter: " + counter);
    }
    // Method to draw rectangles
    StackPane drawGantChart(List<Process> inputs){
        StackPane root = new StackPane();
        root.setPadding(new javafx.geometry.Insets(0)); // Set padding to zero

        // Create an HBox to hold all the rectangles and labels
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(0); // Remove spacing between HBox instances

        // Create rectangles with fixed sizes and labels
//        String[] rectangleNames = {"Rectangle 1", "Rectangle 2", "Rectangle 3"};
// youssef
        timeline = new Timeline(

                new KeyFrame(Duration.seconds(isLive?1:0.005), event -> {
                    String name = schedulerInstance.getProcessNameNow(currentTime) ;
                    if (schedulerInstance.processEmpty()) {
                        avgWaiting.setText("Avg waiting time "+ schedulerInstance.getAverageWaitingTime());
                        avgTurn.setText("Avg turnaround time "+ schedulerInstance.getAverageTurnAroundTime());
                        timeline.stop();// Stop the timeline if flag is false

                    }

                    System.out.println(name);
                    rectangleWithLabel = createRectangleWithLabel(name,
                             xPosition, 60, 40);
                    hbox.getChildren().add(rectangleWithLabel);
                    currentTime++ ;
                    xPosition += 1; // Update the x position for the next rectangle
//                    if (scene3Vbox.getChildren().contains(root)) {
//                        scene3Vbox.getChildren().remove(root); // Removes old Gantt chart if present
//                    }


                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        timeline.play(); // Start the timeline


//            scheduleProcessing(processList,scheduler,currentTime);

        // Add the HBox to the StackPane

        root.getChildren().add(hbox);
        return root ;
    }

    public VBox createRectangleWithLabel(String labelText, double xPosition, double width, double height) {
        // Create a rectangle with fixed dimensions
        Rectangle rectangle = new Rectangle(xPosition * 10, 50, width, height);
        rectangle.setFill(Color.LIGHTBLUE);
        rectangle.setStroke(Color.BLACK);

        // Create a label for the rectangle
        Label label = new Label(labelText);
        label.setTextFill(Color.BLACK);
        label.setAlignment(Pos.CENTER);
        label.setMinSize(width, height); // Ensure label size matches rectangle size

        // Create a StackPane to hold the rectangle and label
        StackPane stackPane = new StackPane();
        stackPane.setTranslateX(xPosition); // Position the StackPane
        stackPane.getChildren().addAll(rectangle, label);
        VBox vbox = new VBox(10);
        Label timing = new Label((String.valueOf(currentTime))) ;
        timing.setAlignment(Pos.CENTER_LEFT);
//        vbox.setSpacing(10);
        vbox.getChildren().addAll(stackPane,timing) ;
        return vbox;
    }
    public List<Process> scheduleProcessing(List<Process> processList, String scheduler , int time) {

        // Instantiate the appropriate scheduler based on the input string
        switch (scheduler) {
            case "FCFS":
                schedulerInstance = new FCFS(processList);
                break;
            case "RR":
                schedulerInstance = new RR(processList);
                break;
            case "SJF":
                schedulerInstance = new SJF(processList);
                break;
            case "SRTF":
                schedulerInstance = new SRTF(processList);
                break;
            case "PP":
                schedulerInstance = new PriorityPrimitiveSchedular(processList);
                break;
            case "PNP":
                schedulerInstance = new PriorityNonPrimitive(processList);
                break;
            default:
                System.out.println("Invalid scheduler type: " + scheduler);
                break;
        }

        // Insert processes into the scheduler
        if (schedulerInstance != null) {
            for (Process p : processList) {
//                schedulerInstance.insertProcess(p.getProcess_name(), time, p.getBurst_time(), 0);
            }
        }

        // Return the list of scheduled processes (if needed)
        return processList;
    }


    public static void main(String[] args) {

        launch();
    }


    @Override
    public void handle(ActionEvent actionEvent) {

    }
}