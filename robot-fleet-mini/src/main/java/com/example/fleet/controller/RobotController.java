package com.example.fleet.controller;

import com.example.fleet.domain.Robot;
import com.example.fleet.domain.Task;
import com.example.fleet.dto.AssignTaskRequest;
import com.example.fleet.dto.AssignTaskResponse;
import com.example.fleet.dto.RobotRegisterRequest;
import com.example.fleet.dto.RobotResponse;
import com.example.fleet.service.RobotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ReqeustMapping("/robots")
public class RobotController{

    private final RobotService robotService;
    private final TaskService taskService;

    public RobotController(
        RobotService robotService
    ){
        this.robotService = robotService;
    }

    @PostMapping
    public ResponseEntity<RobotResponse> register(
        @RequestBody RobotRegisterRequest request
    ){
        Robot robot = robotService.register(
            request.getNumber(),
            request.getName()
        );

        RobotResponse response = RobotResponse.from(robot);

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{number}")
    public ResponseEntity<RobotResponse> findByNumber(
        @PathVariable String number
    ){

        Robot robot = robotService.findByNumber(number);

        RobotResponse response = RobotResponse.from(robot);

        return ResponseEntity.status(200).body(response);
    }

    @PatchMapping("/{robotNumber}/assignment")
    public ResponseEntity<AssignTaskResponse> assignTask(
        @PathVariable String robotNumber,
        @RequestBody AssignTaskRequest request
    ){
        Robot robot = 
            robotService.assignTask(robotNumber, request.getTaskNumber());

        AssignTaskResponse response = 
            AssignTaskResponse.from(robot, robot.getTask());

        return ResponseEntity.status(200).body(response);
    }
}