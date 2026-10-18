package com.example.fleet.dto;

import com.example.fleet.domain.Robot;
import com.example.fleet.domain.RobotStatus;

public class RobotResponse{

    private String robotNumber;
    private String robotName;
    private RobotStatus robotStatus;
    private String taskNumber;

    public RobotResponse(
        String robotNumber,
        String robotName,
        RobotStatus robotStatus,
        String taskNumber
    ){
        this.robotNumber = robotNumber;
        this.robotName = robotName;
        this.robotStatus = robotStatus;
        this.taskNumber = taskNumber;
    }

    public RobotResponse(){
    }

    public static RobotResponse from(Robot robot){

        String taskNumber = 
                robot.getTask() == null
                ? null
                : robot.getTask().getNumber();

        return new RobotResponse(
            robot.getNumber(),
            robot.getName(),
            robot.getRobotStatus(),
            taskNumber
        );
    }

    public String getRobotNumber(){
        return robotNumber;
    }

    public String getRobotName(){
        return robotName;
    }

    public RobotStatus getRobotStatus(){
        return robotStatus;
    }

    public String getTaskNumber(){
        return taskNumber;
    }
}