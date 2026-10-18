package com.example.fleet.dto;

import jakarta.validation.constraints.NotBlank;

public class TaskRegisterRequest{

    @NotBlank(message = "작업 번호는 필수입니다.")
    private String taskNumber;

    @NotBlank(message = "작업 출발지는 필수입니다.")
    private String taskDeparture;

    @NotBlank(message = "작업 도착지는 필수입니다.")
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