package com.example.backend.repository;

import java.util.List;
import com.example.backend.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeatRepository
    extends JpaRepository<Seat, String>{

        public List<Seat> findByReservedFalse();

        public List<Seat> findBySectionAndReservedFalse(String section);

        @Query("""
            SELECT s
            FROM Seat s
            WHERE s.hall.id = :hallId
            AND s.section = 'VIP'
            AND s.reserved = false
            """)
        public List<Seat> findAvailableVipSeatsByHall(
            @Param("hallId") Long hallId
        );
 }
