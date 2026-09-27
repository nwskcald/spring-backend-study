package com.example.fleet.repository;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryRobotRepository implements RobotRepository{

    @Override
    public void save(String robotName){
        System.out.println("로봇 저장: " + robotName);
    }
}