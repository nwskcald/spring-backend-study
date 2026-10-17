package com.example.fleet.repository;

import com.example.fleet.domain.Robot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RobotRepository
    extends JpaRepository<Robot, String>{

        public void existsByNumber(String number);

        public Robot findByNumber(String number);
    }