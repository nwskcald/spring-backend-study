package com.example.fleet.dto;

public class RobotRegisterRequest{

    private String robotNumber;
    private String robotName;

    public RobotRegisterRequest(
        String robotNumber, String robotName
    ){
        this.robotNumber = robotNumber;
        this.robotName = robotName;
    }

    public RobotRegisterRequest(){
    }

    public String getRobotNumber(){
        return robotNumber;
    }

    public String getRobotName(){
        return robotName;
    }

    public void setRobotNumber(String robotNumber){
        this.robotNumber = robotNumber;
    }

    public void setRobotName(String robotName){
        this.robotName = robotName;
    }
}