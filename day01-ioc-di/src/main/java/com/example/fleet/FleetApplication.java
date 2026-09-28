package com.example.fleet;

import com.example.fleet.service.RobotService;
import com.example.fleet.repository.RobotRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.fleet.alert.RobotAlertService;

@SpringBootApplication
public class FleetApplication {

    public static void main(String[] args) {
        SpringApplication.run(FleetApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RobotAlertService robotAlertService) {
        return args -> {
            robotAlertService.reportFailure("R-07");
        };
    }  
}