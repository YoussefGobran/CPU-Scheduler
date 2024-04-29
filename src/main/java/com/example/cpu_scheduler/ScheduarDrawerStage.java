package com.example.cpu_scheduler;

import com.example.cpu_scheduler.Scheduler.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.sql.Time;
import java.util.List;
import java.util.concurrent.Callable;

public class ScheduarDrawerStage {
    static int currentTime = 0;
    static Label timeLabel, avgTurn, avgWaiting;
    static Timeline timeline;
    static Scheduler schedulerInstance;
    static int xPosition=0;
    static StackPane drawGantChart(boolean isLive,Callable<Integer> update_table) {
        StackPane root = new StackPane();
        root.setPadding(new javafx.geometry.Insets(0)); // Set padding to zero

        // Create an HBox to hold all the rectangles and labels
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(0); // Remove spacing between HBox instances


        timeline = new Timeline(

                new KeyFrame(Duration.seconds(isLive ? 1 : 0.001), event -> {
                    String name = schedulerInstance.getProcessNameNow(currentTime);
                    if (schedulerInstance.processEmpty()) {
                        avgWaiting.setText("Avg waiting time " + schedulerInstance.getAverageWaitingTime());
                        avgTurn.setText("Avg turnaround time " + schedulerInstance.getAverageTurnAroundTime());
                        timeline.stop();// Stop the timeline if flag is false

                    }


                    VBox rectangleWithLabel = createRectangleWithLabel(name, xPosition, 60, 40);
                    hbox.getChildren().add(rectangleWithLabel);
                    currentTime++;
                    timeLabel.setText("Counter: " + currentTime);
                    xPosition += 1; // Update the x position for the next rectangle
                    try {
                        update_table.call();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }));
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        timeline.play(); // Start the timeline


//            scheduleProcessing(processList,scheduler,currentTime);

        // Add the HBox to the StackPane

        root.getChildren().add(hbox);
        return root;
    }

    static public VBox createRectangleWithLabel(String labelText, double xPosition, double width, double height) {
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
        Label timing = new Label((String.valueOf(currentTime)));
        timing.setAlignment(Pos.CENTER_LEFT);
//        vbox.setSpacing(10);
        vbox.getChildren().addAll(stackPane, timing);
        return vbox;
    }



        static Stage gantChartStageGenerate(boolean is_live,String scheduler_type, Scheduler schedulerInstanceParm, Callable<Integer> updateTable) {
        currentTime=0;
        xPosition=0;
        timeline=null;
        schedulerInstance=schedulerInstanceParm;
        Stage stage = new Stage();
        stage.setTitle("Gant Chart");
        stage.setOnCloseRequest(event->{
            schedulerInstanceParm.isValid=false;
        });
        Label schedulerLabel = new Label(scheduler_type);
        schedulerLabel.setAlignment(Pos.TOP_RIGHT);

        // Label that updates every second
        timeLabel = new Label("Counter: " + currentTime);
        Button drawGantChartBtn = new Button("Draw Gant Chart");

        avgTurn = new Label();
        avgWaiting = new Label();


        VBox vbox1 = new VBox(20);
        vbox1.setAlignment(Pos.TOP_LEFT);

        vbox1.getChildren().addAll(schedulerLabel, timeLabel, drawGantChartBtn, avgWaiting, avgTurn);
    

// Button handler for drawing the Gantt chart
        drawGantChartBtn.setOnAction(e -> {

            // Create a Timeline with a KeyFrame that runs every 3 seconds
            StackPane root = drawGantChart(is_live,updateTable);
            root.setAlignment(Pos.CENTER_RIGHT);
            vbox1.getChildren().add(root);
        });
        vbox1.setPadding(new Insets(48, 48, 48, 48));
        Scene scene1 = new Scene(vbox1, 800, 800);

// Set the initial scene to the stage
        stage.setScene(scene1);
        stage.show();
        return stage;
    }
}
