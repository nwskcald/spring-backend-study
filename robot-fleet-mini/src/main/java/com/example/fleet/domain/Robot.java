package com.example.fleet.domain;

import com.example.fleet.exception.RobotAlreadyAssignedException;
import com.example.fleet.exception.TaskAlreadyAssignedException;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Robot{

    @Id
    private String number;

    private String name;
    
    @Enumerated(EnumType.STRING)
    private RobotStatus robotStatus;

    @OneToOne(mappedBy = "robot")
    private Task task;

    public Robot(
        String number,
        String name
    ){
        this.number = number;
        this.name = name;
        robotStatus = RobotStatus.IDLE;
    }

    protected Robot(){
    }

    public String getNumber(){
        return number;
    }

    public String getName(){
        return name;
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
            throw new RobotAlreadyAssignedException();
        }

        if(!task.isTaskAvailableToBeAssigned()){
            throw new TaskAlreadyAssignedException();
        }

        this.task = task;
        robotStatus = RobotStatus.BUSY;
        task.assign(this);
    }
}