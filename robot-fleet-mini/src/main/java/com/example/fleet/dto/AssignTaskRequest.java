package com.example.fleet.dto;

public class AssignTaskRequest{

    private String taskNumber;

    public AssignTaskRequest(
        String taskNumber
    ){
        this.taskNumber = taskNumber;
    }

    public AssignTaskRequest(){
    }

    public String getTaskNumber(){
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber){
        this.taskNumber = taskNumber;
    }
}