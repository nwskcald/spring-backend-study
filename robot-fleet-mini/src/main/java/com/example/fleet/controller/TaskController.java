package com.example.fleet.controller;

import com.example.fleet.domain.Task;
import com.example.fleet.dto.TaskRegisterRequest;
import com.example.fleet.dto.TaskResponse;
import com.example.fleet.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController{

    private TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> register(
        @Valid @RequestBody TaskRegisterRequest request
    ){

        Task task = taskService.register(
            request.getTaskNumber(),
            request.getTaskDeparture(),
            request.getTaskDestination()
        );

        TaskResponse response = TaskResponse.from(task);

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{number}")
    public ResponseEntity<TaskResponse> findByNumber(
        @Valid @PathVariable String number
    ){

        Task task = taskService.findByNumber(number);

        TaskResponse response = TaskResponse.from(task);

        return ResponseEntity.status(200).body(response);
    }
}
