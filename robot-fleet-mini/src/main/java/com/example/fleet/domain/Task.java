package com.example.fleet.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Task{

    @Id
    private String number;

    private String departure;
    private String destination;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @OneToOne
    @JoinColumn(name = "robot_number")
    private Robot robot;

    public Task(
        String number,
        String departure,
        String destination
    ){
        this.number = number;
        this.departure = departure;
        this.destination = destination;
        taskStatus = TaskStatus.PENDING;
    }

    protected Task(){
    }

    public String getNumber(){
        return number;
    }

    public String getDeparture(){
        return departure;
    }

    public String getDestination(){
        return destination;
    }

    public TaskStatus getTaskStatus(){
        return taskStatus;
    }

    public Robot getRobot(){
        return robot;
    }

    // 작업 할당 가능 여부 검사
    public boolean isTaskAvailableToBeAssigned(){
        return taskStatus == TaskStatus.PENDING;
    }

    // 작업 할당 해제 가능 여부 검사
    public boolean isTaskAvailableToBeUnAssigned(){
        return taskStatus == TaskStatus.ASSIGNED;
    }

    // 작업 할당
    public void assign(Robot robot){
        this.robot = robot;
        taskStatus = TaskStatus.ASSIGNED;
    }

    // 작업 할당 해제
    public void unAssign(){
        taskStatus = TaskStatus.PENDING;
        this.robot = null;
    }
}