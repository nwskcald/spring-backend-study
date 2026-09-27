package com.example.fleet;

import com.example.fleet.service.RobotService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FleetApplication {

    public static void main(String[] args) {
        SpringApplication.run(FleetApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RobotService robotService) {
        return args -> robotService.register("R-01");
    }
}