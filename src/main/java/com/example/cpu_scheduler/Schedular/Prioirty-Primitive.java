package com.example.cpu_scheduler.Schedular;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import javafx.print.Collation;

class PriorityPrimitiveSchedular{
  PriorityQueue<ProcessPriority> queue;
  List<ProcessPriority> processesList;
  int counter=0;
  PriorityPrimitiveSchedular(ProcessPriority[] proArr){
    queue=new PriorityQueue<>(
      new Comparator<ProcessPriority>() {
        public int compare(ProcessPriority p1, ProcessPriority p2)  {
          int res=p1.priority-p2.priority;
          if(res==0){
            res+=(p1.arrivalTime-p2.arrivalTime);
          }
          return res;
        }
    }
    );
    processesList=new ArrayList<ProcessPriority>(proArr.length);
    for(int i=0;i<proArr.length;i++){
      processesList.add(proArr[i]);
    }
    Collections.sort(processesList, new Comparator<ProcessPriority>() {
      public int compare(ProcessPriority p1, ProcessPriority p2)  {
        int res=p1.arrivalTime-p2.arrivalTime;
        if(res==0){
          res+=(p1.burstTime-p2.burstTime);
        }
        return res;
      }
  });
  }

  boolean processEmpty(){
    return queue.isEmpty();
  }
  String getProcessNameNow(int currentTime){
    while(counter<processesList.size()&&currentTime>processesList.get(counter).arrivalTime){
      queue.offer(processesList.get(counter));
      counter++;
    }
    ProcessPriority p=queue.poll();
    p.burstTime--;
    if(p.burstTime>=1){
      queue.offer(p);
    }
    return p.name;
  }
  void insertProcess(String name, int currentTime,int burstTime,int priority){
    processesList.add(counter,new ProcessPriority(name,currentTime,burstTime,priority));
  }
}

class ProcessPriority{
  String name;
  int arrivalTime;
  int burstTime;
  int priority;
  ProcessPriority(String name,int arrivalTime,int burstTime,int priority){
    this.name=name;
    this.arrivalTime=arrivalTime;
    this.burstTime=burstTime;
    this.priority=priority;
  }
}