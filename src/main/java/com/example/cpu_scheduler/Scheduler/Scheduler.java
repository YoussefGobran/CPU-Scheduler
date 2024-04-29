package com.example.cpu_scheduler.Scheduler;

import com.example.cpu_scheduler.Process;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Scheduler {
  int counter=0;
  public boolean isValid=true;
  public List<Process> processesList;

  // If there is any processes left ro run
  public abstract boolean processEmpty();

  // which process to run at the current time
  // Note that the function needs to be called (1), (2) and so forth
  // if no process now, will return empty string ""
  public abstract String getProcessNameNow(int currentTime);

  // Insert process name ,current time, burst time and if the scheduler don't use the priority give any arbitrary value

  public void insertProcess(
          Process p
  ) {
    if(counter>=processesList.size()){
      p.setArrival_time(
              Math.max(
                      p.getArrival_time(),
                      processesList.get(processesList.size()-1).getArrival_time()
              )
      );
    }else{
      p.setArrival_time(
              Math.max(
                      p.getArrival_time(),
                      processesList.get(counter).getArrival_time()
              )
      );

    }

    processesList.add(
            counter,
            p
    );
    sortList();
  }
  public void sortList(){
    Collections.sort(
            processesList,
            new Comparator<Process>() {
              public int compare(Process p1, Process p2) {
                int res = p1.getArrival_time() - p2.getArrival_time();
                if (res == 0) {
                  res += (p1.getBurst_time() - p2.getBurst_time());
                }
                return res;
              }
            }
    );
  }

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
