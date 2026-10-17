package com.example.fleet.dto;

public class TaskRegisterRequest{

    private String taskNumber;
    private String taskDeparture;
    private String taskDestination;

    public TaskRegisterRequest(
        String taskNumber,
        String taskDeparture,
        String taskDestination
    ){
        this.taskNumber = taskNumber;
        this.taskDeparture = taskDeparture;
        this.taskDestination = taskDestination;
    }

    public TaskRegisterRequest(){
    }

    public String getTaskNumber(){
        return taskNumber;
    }

    public String getTaskDeparture(){
        return taskDeparture;
    }

    public String getTaskDestination(){
        return taskDestination;
    }

    public void setTaskNumber(String taskNumber){
        this.taskNumber = taskNumber;
    }

    public void setTaskDeparture(String taskDeparture){
        this.taskDeparture = taskDeparture;
    }

    public void setTaskDestination(String taskDestination){
        this.taskDestination = taskDestination;
    }
}