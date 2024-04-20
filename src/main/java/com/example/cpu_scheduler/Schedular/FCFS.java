package com.example.cpu_scheduler.Schedular;

import com.example.cpu_scheduler.Process;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FCFS extends Schedular {

  Queue<Process> queue;

  int counter = 0;
  Process p;

  FCFS(List<Process> proArr) {
    queue = new LinkedList<>();

    processesList = new ArrayList<Process>(proArr.size());
    for (int i = 0; i < proArr.size(); i++) {
      processesList.add(proArr.get(i));
    }

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

  @Override
  public boolean processEmpty() {
    return queue.isEmpty() && counter == processesList.size();
  }

  @Override
  public String getProcessNameNow(int currentTime) {
    while (
      counter < processesList.size() &&
      currentTime > processesList.get(counter).getArrival_time()
    ) {
      queue.offer(processesList.get(counter));
      counter++;
    }
    String res = "";
    if (p == null) {
      if (queue.isEmpty()) {
        return "";
      }
      p = queue.poll();
    }
    p.setBurst_time(p.getBurst_time() - 1);
    res = p.getProcess_name();
    if (p.getBurst_time() <= 0) {
      p.setFinalTime(currentTime + 1);
      p = null;
    }
    return res;
  }

  @Override
  public void insertProcess(
    String name,
    int currentTime,
    int burstTime,
    int priority
  ) {
    processesList.add(
      counter,
      new Process(name, currentTime, burstTime, priority)
    );
  }
}