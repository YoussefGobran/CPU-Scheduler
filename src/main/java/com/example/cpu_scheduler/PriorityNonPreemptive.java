package com.example.cpu_scheduler;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityNonPreemptive {
    List<Process> processes;
    PriorityQueue<Process> queue;
    int counter=0;

    PriorityNonPreemptive(List<Process> prolist){
        this.processes=prolist;

        queue=new PriorityQueue<>(
                new Comparator<Process>() {
                    public int compare(Process p1, Process p2)  {
                        int res = p1.getPriority() - p2.getPriority();
                        if(res==0){
                            res+=(p1.getBurst_time() - p2.getBurst_time());
                        }
                        return res;
                    }
                }
        );

        Collections.sort(processes, new Comparator<Process>() {
            public int compare(Process p1, Process p2)  {
                int res= p1.getArrival_time() - p2.getArrival_time();
                if(res==0){
                    res+=(p1.getPriority() - p2.getPriority());
                }
                return res;
            }
        });


    }


    String getProcessNameNow(int currentTime){
        while(counter<processes.size() && currentTime>=processes.get(counter).getArrival_time()){
            queue.offer(processes.get(counter));
            counter++;
        }

        Process p = null;
        int temptime = 0;
        while(!queue.isEmpty()) {
            p = queue.peek();
            if(currentTime>=(p.getBurst_time()+temptime)){
                temptime = temptime + p.getBurst_time();
                queue.poll();
            }
            else{

                return p.getProcess_name();
            }
        }
        return null;
    }


    void insertProcess(String name, int currentTime,int burstTime,int priority){
        processes.add(counter,new Process(name,currentTime,burstTime,priority));
    }
}
