package com.example.cpu_scheduler.Schedular;

import com.example.cpu_scheduler.Process;
import java.util.List;

public abstract class Schedular {

  List<Process> processesList;

  // If there is any processes left ro run
  public abstract boolean processEmpty();

  // what process to run at the current time
  // Note that the function needs to be called (1), (2) and so forth
  // if no process now, will return empty string ""
  public abstract String getProcessNameNow(int currentTime);

  // Insert process name ,current time, burst time and if the schedular don't use the priority give any arbitrary value
  public abstract void insertProcess(
    String name,
    int currentTime,
    int burstTime,
    int priority
  );

  public double getAverageWaitingTime() {
    double sum = 0;
    for (Process p : processesList) {
      sum += p.getWaitingTime();
    }
    return sum / processesList.size();
  }

  public double getAverageTurnAroundTime() {
    double sum = 0;
    for (Process p : processesList) {
      sum += p.getTurnAroundTime();
    }
    return sum / processesList.size();
  }
}
