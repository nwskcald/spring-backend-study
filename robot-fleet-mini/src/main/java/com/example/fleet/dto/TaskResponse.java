package com.example.fleet.dto;

import com.example.fleet.domain.Task;

public class TaskResponse{

    private String taskNumber;
    private String taskDeparture;
    private String taskDestination;
    private String taskStatus;
    private String robotNumber;

    public TaskResponse(
        String taskNumber,
        String taskDeparture,
        String taskDestination,
        String taskStatus,
        String robotNumber
    ){
        this.taskNumber = taskNumber;
        this.taskDeparture = taskDeparture;
        this.taskDestination = taskDestination;
        this.taskStatus = taskStatus;
        this.robotNumber = robotNumber;
    }

    public TaskResponse(){
    }

    public static TaskResponse from(Task task){
        return new TaskResponse(
            task.getNumber(),
            task.getDeparture(),
            task.getDestination(),
            task.getTaskStatus(),
            task.getRobot().getNumber()
        );
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

    public String getTaskStatus(){
        return taskStatus;
    }

    public String getRobotNumber(){
        return robotNumber;
    }
}