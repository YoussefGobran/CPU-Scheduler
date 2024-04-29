package com.example.cpu_scheduler.Scheduler;

import com.example.cpu_scheduler.Process;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FCFS extends Scheduler {

  Queue<Process> queue;

  Process p;

  public FCFS(List<Process> proArr) {
    queue = new LinkedList<>();

    processesList = new ArrayList<Process>(proArr.size());
    for (int i = 0; i < proArr.size(); i++) {
      processesList.add(proArr.get(i));
    }

    sortList();
  }

  @Override
  public boolean processEmpty() {
    return queue.isEmpty() && counter == processesList.size() &&p==null;
  }

  @Override
  public String getProcessNameNow(int currentTime) {
    while (
      counter < processesList.size() &&
      currentTime >= processesList.get(counter).getArrival_time()
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

}
