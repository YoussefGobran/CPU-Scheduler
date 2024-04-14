package com.example.cpu_scheduler;

public class Process {
    //data field of any process
    private int burst_time;
    private int priority;
    private int arrival_time;
    private int remaining ;
    //constructors
    //In case of FCFS or RoundRobin
    Process(int aTime , int bTime)
    {
        this.arrival_time=aTime;
        this.burst_time= bTime;
        this.priority=0;
        remaining = bTime;
    }
    //In case of priority or sjf in both cases
    Process(int aTime , int bTime,int pri)
    {
        this.arrival_time=aTime;
        this.burst_time= bTime;
        this.priority=pri;
        remaining = bTime;
    }

    //getters
    public int getRemaining() {
        return remaining;
    }
    //decrement Remaining time
    public void decRemaining ()
    {
        remaining--;
    }
}
