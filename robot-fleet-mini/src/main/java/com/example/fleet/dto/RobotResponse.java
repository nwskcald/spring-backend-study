package com.example.fleet.dto;

import com.example.fleet.domain.Robot;

public class RobotResponse{

    private String robotNumber;
    private String robotName;
    private String robotStatus;
    private String taskNumber;

    public RobotResponse(
        String robotNumber,
        String robotName,
        String robotStatus,
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
        return new RobotResponse(
            robot.getNumber(),
            robot.getName(),
            robot.getRobotStatus(),
            robot.getTask().getNumber()
        );
    }

    public String getRobotNumber(){
        return robotNumber;
    }

    public String getRobotName(){
        return robotName;
    }

    public String getRobotStatus(){
        return robotStatus;
    }

    public String getTaskNumber(){
        return taskNumber;
    }
}