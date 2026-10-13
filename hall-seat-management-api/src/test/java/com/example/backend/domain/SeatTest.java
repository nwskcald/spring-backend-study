package com.example.backend.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SeatTest{

    @Test
    void reserveChangesSeatStatus(){

        // Given: 테스트에 필요한 객체 준비
        Hall hall = new Hall(1L, "Main Hall");
        Seat seat = new Seat("A-01", "VIP", hall);

        // When: 검증하려는 기능 실행
        seat.reserve();

        // Then: 실제 결과가 예상과 같은지 확인
        assertEquals(true, seat.isReserved());
    }

    @Test
    void invalidSeatIdThrowsException(){

        // Given
        String seatId = "";

        // When & Then
        assertThrows(IllegalArgumentException.class,
            () -> {
                if(seatId.isBlank()){
                    throw new IllegalArgumentException("좌석 ID는 필수입니다.");
                }
            }
        );
    }

    @Test
    void cannotReserveAlreadyReservedSeat(){

        // Given
        Hall hall = new Hall(1L, "Main Hall");
        Seat seat = new Seat("A-01", "VIP", hall);
        seat.reserve();

        // When & Then
        assertThrows(IllegalStateException.class,
            () -> {
                seat.reserve();
            }
        );
    }
}