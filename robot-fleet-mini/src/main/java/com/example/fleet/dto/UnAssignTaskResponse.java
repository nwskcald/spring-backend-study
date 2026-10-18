package com.example.fleet.dto;

import com.example.fleet.domain.Robot;
import com.example.fleet.domain.Task;
import com.example.fleet.domain.RobotStatus;
import com.example.fleet.domain.TaskStatus;

public class UnAssignTaskResponse{

    private String robotNumber;
    private RobotStatus robotStatus;
    private String taskNumber;
    private TaskStatus taskStatus;

    public UnAssignTaskResponse(
        String robotNumber,
        RobotStatus robotStatus,
        String taskNumber,
        TaskStatus taskStatus
    ){
        this.robotNumber = robotNumber;
        this.robotStatus = robotStatus;
        this.taskNumber = taskNumber;
        this.taskStatus = taskStatus;
    }

    public UnAssignTaskResponse(){
    }

    public static UnAssignTaskResponse from(Robot robot, Task task){
        return new UnAssignTaskResponse(
            robot.getNumber(),
            robot.getRobotStatus(),
            task.getNumber(),
            task.getTaskStatus()
        );
    }

    public String getRobotNumber(){
        return robotNumber;
    }

    public RobotStatus getRobotStatus(){
        return robotStatus;
    }

    public String getTaskNumber(){
        return taskNumber;
    }

    public TaskStatus getTaskStatus(){
        return taskStatus;
    }
}