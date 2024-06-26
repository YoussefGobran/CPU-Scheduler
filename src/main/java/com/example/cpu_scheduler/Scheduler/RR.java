package com.example.cpu_scheduler.Scheduler;

import com.example.cpu_scheduler.Process;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RR extends Scheduler {

  Queue<Process> queue;
  public RR(List<Process> proArr) {
    queue = new LinkedList<>();

    processesList = new ArrayList<Process>(proArr.size());
    for (int i = 0; i < proArr.size(); i++) {
      processesList.add(proArr.get(i));
    }

    sortList();
  }

  @Override
  public boolean processEmpty() {
    return queue.isEmpty() && counter == processesList.size();
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
    if (queue.isEmpty()) {
      return "";
    }
    Process p = queue.poll();
    p.setBurst_time(p.getBurst_time() - 1);
    String res = p.getProcess_name();
    if (p.getBurst_time() > 0) {
      queue.offer(p);
    } else {
      p.setFinalTime(currentTime + 1);
    }
    return res;
  }


}
