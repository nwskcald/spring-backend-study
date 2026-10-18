package com.example.fleet.dto;

import jakarta.validation.constraints.NotBlank;

public class RobotRegisterRequest{

    @NotBlank(message = "로봇 번호는 필수입니다.")
    private String robotNumber;

    @NotBlank(message = "로봇 이름은 필수입니다.")
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