package com.example.cpu_scheduler.Schedular;

import com.example.cpu_scheduler.Process;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class SRTF extends Schedular {

  PriorityQueue<Process> queue;
  List<Process> processesList;
  int counter = 0;

  SRTF(List<Process> proArr) {
    queue =
      new PriorityQueue<>(
        new Comparator<Process>() {
          public int compare(Process p1, Process p2) {
            int res = p1.getBurst_time() - p2.getBurst_time();
            if (res == 0) {
              res += (p1.getArrival_time() - p2.getArrival_time());
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
  boolean processEmpty() {
    return queue.isEmpty() && counter == processesList.size();
  }

  @Override
  String getProcessNameNow(int currentTime) {
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
    }
    return p.getProcess_name();
  }

  @Override
  void insertProcess(
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
