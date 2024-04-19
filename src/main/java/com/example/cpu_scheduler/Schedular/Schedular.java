package com.example.cpu_scheduler.Schedular;

public abstract class Schedular {

  // If there is any processes left ro run
  abstract boolean processEmpty();
  
  // what process to run at the current time
  // Note that the function needs to be called (1), (2) and so forth
  // if no process now, will return empty string ""
  abstract String getProcessNameNow(int currentTime);

  // Insert process name ,current time, burst time and if the schedular don't use the priority give any arbitrary value
  abstract void insertProcess(
    String name,
    int currentTime,
    int burstTime,
    int priority
  );
}
