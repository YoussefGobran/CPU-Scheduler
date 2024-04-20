package com.example.cpu_scheduler;

public class Process {
    //data field of any process
    private String process_name ;
    private int burst_time;
    private int priority ;
    private int arrival_time;
    private int final_time;
    private int original_burst_time;
    //constructors
    //In case of FCFS or RoundRobin
    public Process(String pName , int aTime , int bTime)
    {
        this.process_name = pName;
        this.arrival_time=aTime;
        this.burst_time= bTime;
        this.priority=0;
        this.original_burst_time=bTime;
        this.final_time=0;
    }
    //In case of priority or sjf in both cases
    public Process(String pName , int aTime , int bTime,int pri)
    {
        this.process_name = pName;
        this.arrival_time=aTime;
        this.burst_time= bTime;
        this.priority = pri;
        this.original_burst_time=bTime;
        this.final_time=0;
    }

    //getters
 
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


    public String getProcess_name() {
        return process_name;
    }

    public void setProcess_name(String process_name) {
        this.process_name = process_name;
    }

    public int getFinalTime() {
        return this.final_time;
    }
    public void setFinalTime(int final_time) {
        this.final_time=final_time;
    }
    
    // Methods
    public int getWaitingTime(){
        return this.final_time-this.arrival_time-this.original_burst_time;
    }
    public int getTurnAroundTime(){
        return this.final_time-this.arrival_time;
    }
}
