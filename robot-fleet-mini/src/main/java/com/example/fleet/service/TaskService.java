package com.example.fleet.service;

import com.example.fleet.domain.Task;
import com.example.fleet.exception.DuplicateTaskException;
import com.example.fleet.exception.TaskNotFoundException;
import com.example.fleet.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService{

    private final TaskRepository taskRepository;

    public TaskService(
        TaskRepository taskRepository
    ){
        this.taskRepository = taskRepository;
    }

    // 작업 등록
    @Transactional
    public Task register(
        String number,
        String departure,
        String destination
    ){
        if(taskRepository.existsByNumber(number)){
            throw new DuplicateTaskException();
        }

        Task task = new Task(number, departure, destination);

        return taskRepository.save(task);
    }

    // 작업 조회
    public Task findByNumber(String number){

        Task task = taskRepository.findByNumber(number)
            .orElseThrow(
                () -> new TaskNotFoundException()
            );
        
        return task;
    }
}