package com.example.backend.repository;

import com.example.backend.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository
    extends JpaRepository<Seat, String>{
 }
