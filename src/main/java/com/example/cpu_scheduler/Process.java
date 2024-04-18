package com.example.cpu_scheduler;

public class Process {
    //data field of any process
    private String process_name ;
    private int burst_time;
    private int priority ;
    private int arrival_time;
    private int remaining ;
    //constructors
    //In case of FCFS or RoundRobin
     Process(String pName , int aTime , int bTime)
    {
        this.process_name = pName;
        this.arrival_time=aTime;
        this.burst_time= bTime;
        remaining = bTime;
    }
    //In case of priority or sjf in both cases
     Process(String pName , int aTime , int bTime,int pri)
    {
        this.process_name = pName;
        this.arrival_time=aTime;
        this.burst_time= bTime;
        this.priority = pri;
        remaining = bTime;
    }

    //getters
    public int getRemaining() {
        return remaining;
    }

    public int getBurst_time() {
        return burst_time;
    }

    public void setBurst_time(int burst_time) {
        this.burst_time = burst_time;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(int arrival_time) {
        this.arrival_time = arrival_time;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public String getProcess_name() {
        return process_name;
    }

    public void setProcess_name(String process_name) {
        this.process_name = process_name;
    }

    //decrement Remaining time
    public void decRemaining ()
    {
        remaining--;
    }
}
