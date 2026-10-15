package com.example.backend.service;

import com.example.backend.domain.Hall;
import com.example.backend.domain.Seat;
import com.example.backend.repository.HallRepository;
import com.example.backend.repository.SeatRepository;
import com.example.backend.exception.SeatNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest{

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private HallRepository hallRepository;

    @InjectMocks
    private SeatService seatService;

    @Test
    void findByIdReturnsSeat(){

        // Given: 테스트 환경준비
        Hall hall = new Hall(1L, "Main Hall");
        Seat seat = new Seat("A-01", "VIP", hall);

        when(seatRepository.findById("A-01"))
            .thenReturn(Optional.of(seat));

        // When: 실제 Service 실행
        Seat result = seatService.findById("A-01");

        // Then: 결과 검증
        assertEquals("A-01", result.getId());
        assertEquals("VIP", result.getSection());

        verify(seatRepository).findById("A-01");
    }

    @Test
    void findByIdThrowsWhenSeatDoesNotExist(){

        // Given
        when(seatRepository.findById("C-404"))
            .thenReturn(Optional.empty());

        // When & Then
        assertThrows(
            SeatNotFoundException.class,
            () -> seatService.findById("C-404")
        );

        verify(seatRepository).findById("C-404");
    }
}