package com.example.backend.repository;

import java.util.List;
import com.example.backend.domain.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HallRepository 
    extends JpaRepository<Hall, Long>{

        @Query("SELECT DISTINCT h FROM Hall h LEFT JOIN FETCH h.seats")
        List<Hall> findAllWithSeats();
}