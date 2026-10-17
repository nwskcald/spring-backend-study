package com.example.fleet.service;

import com.example.fleet.domain.Robot;
import com.example.fleet.domain.Task;
import com.example.fleet.exception.DuplicateRobotException;
import com.example.fleet.exception.RobotNotFoundException;
import com.example.fleet.exception.TaskNotFoundException;
import com.example.fleet.repository.RobotRepository;
import com.example.fleet.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RobotService{

    private final RobotRepository robotRepository;
    private final TaskRepository taskRepository;

    public RobotService(
        RobotRepository robotRepository,
        TaskRepository taskRepository
    ){
        this.robotRepository = robotRepository;
        this.taskRepository = taskRepository;
    }

    // 로봇 등록
    @Transactional
    public Robot register(
        String number, String name
    ){
        if(robotRepository.existsByNumber(number)){
            throw new DuplicateRobotException();
        }

        Robot robot = new Robot(number, name);

        return robotRepository.save(robot);
    }

    // 로봇 조회
    public Robot findByNumber(String number){

        Robot robot = robotRepository.findByNumber(number)
            .orElseThrow(
                () -> new RobotNotFoundException()
            );
        
        return robot;
    }

    // 로봇에게 작업 배정
    @Transactional
    public Robot assignTask(String robotNumber, String taskNumber){

        Robot robot = robotRepository.findByNumber(robotNumber)
            .orElseThrow(
                () -> new RobotNotFoundException()
            );
        
        Task task = taskRepository.findByNumber(taskNumber)
            .orElseThrow(
                () -> new TaskNotFoundException()
            );
        
        robot.assign(task);

        // Dirty Checking
        // Hibernate이 commit 이후 Repository 변경

        return robot;
    }
}