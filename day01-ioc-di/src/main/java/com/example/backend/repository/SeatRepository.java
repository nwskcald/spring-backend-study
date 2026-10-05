package com.example.backend.repository;

import com.example.backend.domain.Seat;
import java.util.Optional;

public interface SeatRepository{

    void save(Seat seat);
    
    Optional<Seat> findById(String id);

    boolean existsById(String id);
}