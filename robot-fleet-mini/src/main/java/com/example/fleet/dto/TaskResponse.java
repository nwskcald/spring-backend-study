package com.example.fleet.dto;

import com.example.fleet.domain.Task;
import com.example.fleet.domain.TaskStatus;

public class TaskResponse{

    private String taskNumber;
    private String taskDeparture;
    private String taskDestination;
    private TaskStatus taskStatus;
    private String robotNumber;

    public TaskResponse(
        String taskNumber,
        String taskDeparture,
        String taskDestination,
        TaskStatus taskStatus,
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

        String robotNumber = 
            task.getRobot() == null
            ? null
            : task.getRobot().getNumber();

        return new TaskResponse(
            task.getNumber(),
            task.getDeparture(),
            task.getDestination(),
            task.getTaskStatus(),
            robotNumber
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

    public TaskStatus getTaskStatus(){
        return taskStatus;
    }

    public String getRobotNumber(){
        return robotNumber;
    }
}