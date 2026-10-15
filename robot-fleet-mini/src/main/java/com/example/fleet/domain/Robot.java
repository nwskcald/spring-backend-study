package com.example.fleet.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
public class Robot{

    @Id
    private String robotNumber;

    private String robotName;
    
    @Enumerated(EnumType.STRING)
    private RobotStatus robotStatus;

    @OneToOne(mappedBy = "robot")
    private Task task;

    public Robot(
        String robotNumber,
        String robotName
    ){
        this.robotNumber = robotNumber;
        this.robotName = robotName;
        robotStatus = RobotStatus.IDLE;
    }

    protected Robot(){
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

    public Task getTask(){
        return task;
    }

    // 로봇 배정 가능 여부 검사 메소드
    public boolean isRobotAvailableToAssign(){
        return robotStatus == RobotStatus.IDLE;
    }

    // 로봇 배정 후 상태 변경 메소드
    public void assign(Task task){

        if(!isRobotAvailableToAssign()){
            throw new IllegalStateException(
                "이미 작업 중인 로봇입니다."
            );
        }

        if(!task.isTaskAvailableToBeAssigned()){
            throw new IllegalStateException(
                "이미 배정된 작업입니다."
            );
        }

        this.task = task;
        robotStatus = RobotStatus.BUSY;
        task.assign(this);
    }
}