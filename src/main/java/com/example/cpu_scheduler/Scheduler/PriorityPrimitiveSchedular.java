package com.example.cpu_scheduler.Scheduler;

import com.example.cpu_scheduler.Process;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityPrimitiveSchedular extends Scheduler {

  PriorityQueue<Process> queue;
  int counter = 0;

  public PriorityPrimitiveSchedular(List<Process> proArr) {
    queue =
      new PriorityQueue<>(
        new Comparator<Process>() {
          public int compare(Process p1, Process p2) {
            int res = p1.getPriority() - p2.getPriority();
            if (res == 0) {
              res += (p1.getBurst_time() - p2.getBurst_time());
            }
            return res;
          }
        }
      );
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

    if (queue.isEmpty()) {
      return "";
    }
    Process p = queue.poll();
    p.setBurst_time(p.getBurst_time() - 1);
    if (p.getBurst_time() >= 1) {
      queue.offer(p);
    } else {
      p.setFinalTime(currentTime + 1);
    }
    return p.getProcess_name();
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
