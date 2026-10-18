package com.example.fleet.dto;

import jakarta.validation.constraints.NotBlank;

public class AssignTaskRequest{

    @NotBlank(message = "작업 번호는 필수입니다.")
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