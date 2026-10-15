package com.example.fleet.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
public class Task{

    @Id
    private String taskNumber;

    private String taskDeparture;
    private String taskDestination;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @OneToOne
    @JoinColumn(name = "robot_number")
    private Robot robot;

    public Task(
        String taskNumber,
        String taskDeparture,
        String taskDestination
    ){
        this.taskNumber = taskNumber;
        this.taskDeparture = taskDeparture;
        this.taskDestination = taskDestination;
        taskStatus = TaskStatus.PENDING;
    }

    protected Task(){
    }

    public String getTaskNumber(){
        return taskNumber;
    }

    public String getTaskDeparture(){
        return taskDeparture;
    }

    public String getTaskDestination(){
        return taskDestination;
    }

    public TaskStatus getTaskStatus(){
        return taskStatus;
    }

    public Robot getRobot(){
        return robot;
    }

    // 작업 배정 가능 여부 검사 메소드
    public boolean isTaskAvailableToBeAssigned(){
        return taskStatus == TaskStatus.PENDING;
    }

    // 작업 배정 후 상태 변경 메소드
    public void assign(Robot robot){
        this.robot = robot;
        taskStatus = TaskStatus.ASSIGNED;
    }

}