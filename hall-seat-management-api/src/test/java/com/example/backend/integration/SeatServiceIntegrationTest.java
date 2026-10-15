package com.example.backend.integration;

import com.example.backend.domain.Hall;
import com.example.backend.domain.Seat;
import com.example.backend.repository.HallRepository;
import com.example.backend.repository.SeatRepository;
import com.example.backend.service.SeatService;
import com.example.backend.exception.SeatNotFoundException;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class SeatServiceIntegrationTest{

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatService seatService;

    @Test
    void deleteSeatRemovesItFromDatabase(){

        // Given: 실제 DB에 데이터 저장
        Hall hall = new Hall(1L, "Main Hall");
        hallRepository.save(hall);

        Seat seat = new Seat("A-01", "VIP", hall);
        seatRepository.save(seat);

        assertTrue(seatRepository.existsById("A-01"));

        // When: 실제 Service로 삭제
        seatService.delete("A-01");

        // Then: 실제 Repository로 삭제 여부 확인
        assertFalse(seatRepository.existsById("A-01"));
    }

    @Test
    void deleteNonExistentSeatThrowsException(){

        // Given

        // When & Then
        assertThrows(SeatNotFoundException.class,
            () -> seatService.delete("C-404")
        );
    }
}