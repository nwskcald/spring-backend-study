package com.example.fleet.repository;

import com.example.fleet.domain.Task;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository
    extends JpaRepository<Task, String>{

        public boolean existsByNumber(String number);

        public Optional<Task> findByNumber(String number);
   }