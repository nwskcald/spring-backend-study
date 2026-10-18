package com.example.fleet.dto;

import com.example.fleet.domain.Robot;
import com.example.fleet.domain.Task;
import com.example.fleet.domain.RobotStatus;
import com.example.fleet.domain.TaskStatus;

public class AssignTaskResponse{

    private String robotNumber;
    private RobotStatus robotStatus;
    private String taskNumber;
    private TaskStatus taskStatus;

    public AssignTaskResponse(
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