package com.example.backend.repository;

import java.util.List;
import com.example.backend.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SeatRepository
    extends JpaRepository<Seat, String>{

        public List<Seat> findByReservedFalse();

        public Page<Seat> findByReservedFalse(Pageable pageable);
        
        public List<Seat> findBySectionAndReservedFalse(String section);

        public Page<Seat> findBySectionAndReservedFalse(
            String section, Pageable pageable
        );

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
