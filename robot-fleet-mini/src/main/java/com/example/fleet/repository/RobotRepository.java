package com.example.fleet.repository;

import com.example.fleet.domain.Robot;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RobotRepository
    extends JpaRepository<Robot, String>{

        public boolean existsByNumber(String number);

        public Optional<Robot> findByNumber(String number);
    }