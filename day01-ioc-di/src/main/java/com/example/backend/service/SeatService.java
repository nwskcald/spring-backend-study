package com.example.backend.service;

import com.example.backend.domain.Seat;
import com.example.backend.exception.DuplicateSeatException;
import com.example.backend.exception.SeatNotFoundException;
import com.example.backend.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeatService{

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository){
        this.seatRepository = seatRepository;
    }

    public Seat register(String id, String section){
        if(seatRepository.existsById(id)){
            throw new DuplicateSeatException(id);
        } 

        Seat seat = new Seat(id, section);
        seatRepository.save(seat);
        return seat;
        
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