package com.example.fleet.dto;

import com.example.fleet.domain.Robot;
import com.example.fleet.domain.Task;

public class AssignTaskResponse{

    private String robotNumber;
    private String robotStatus;
    private String taskNumber;
    private String taskStatus;

    public AssignTaskResponse(
        String robotNumber,
        String robotStatus,
        String taskNumber,
        String taskStatus
    ){
        this.robotNumber = robotNumber;
        this.robotStatus = robotStatus;
        this.taskNumber = taskNumber;
        this.taskStatus = taskStatus;
    }

    public AssignTaskResponse(){
    }

    public static AssignTaskResponse from(Robot robot, Task task){
        return new AssignTaskResponse(
            robot.getNumber(),
            robot.getRobotStatus(),
            task.getNumber(),
            task.getTaskStatus()
        );
    }

    public String getRobotNumber(){
        return robotNumber;
    }

    public String getRobotStatus(){
        return robotStatus;
    }

    public String getTaskNumber(){
        return taskNumber;
    }

    public String getTaskStatus(){
        return taskStatus;
    }
}