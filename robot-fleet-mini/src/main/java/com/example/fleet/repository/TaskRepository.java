package com.example.fleet.repository;

import com.example.fleet.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository
    extends JpaRepository<Task, String>{

        public void existsByNumber(String number);

        public Task findByNumber(String number);
   }