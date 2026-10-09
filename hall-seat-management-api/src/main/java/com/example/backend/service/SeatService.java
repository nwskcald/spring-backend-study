package com.example.backend.service;

import com.example.backend.domain.Seat;
import com.example.backend.domain.Hall;
import com.example.backend.exception.DuplicateSeatException;
import com.example.backend.exception.SeatNotFoundException;
import com.example.backend.repository.SeatRepository;
import com.example.backend.repository.HallRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeatService{

    private final SeatRepository seatRepository;
    private final HallRepository hallRepository;

    public SeatService(
        SeatRepository seatRepository,
        HallRepository hallRepository
    ){
        this.seatRepository = seatRepository;
        this.hallRepository = hallRepository;
    }

    @Transactional
    public Seat register(
        String seatId,
        String section,
        Long hallId
    ){
        if(seatRepository.existsById(seatId)){
            throw new DuplicateSeatException(seatId);
        } 

        Hall hall = hallRepository.findById(hallId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    "존재하지 않는 공연장입니다."
                )
            );
        
        Seat seat = new Seat(seatId, section, hall);

        return seatRepository.save(seat);
    }

    public Seat findById(String id){

        return seatRepository.findById(id)
            .orElseThrow(() -> new SeatNotFoundException(id));
    }

    @Transactional
    public Seat changeSection(String id, String newSection){

        Seat seat = seatRepository.findById(id)
            .orElseThrow(() -> new SeatNotFoundException(id));

        seat.changeSection(newSection);

        return seat;
    }
}