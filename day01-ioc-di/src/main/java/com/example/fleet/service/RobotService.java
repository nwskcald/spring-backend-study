package com.example.fleet.service;

import com.example.fleet.repository.RobotRepository;
import org.springframework.stereotype.Service;

@Service
public class RobotService{

    private final RobotRepository robotRepository;

    public RobotService(RobotRepository robotRepository){
        this.robotRepository = robotRepository;
    }

    public void register(String robotName){
        System.out.println("로봇 등록 시작");
        robotRepository.save(robotName);
    }
}