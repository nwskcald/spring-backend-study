package com.example.backend.service;

import com.example.backend.domain.Hall;
import com.example.backend.repository.HallRepository;
import com.example.backend.exception.DuplicateHallException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HallService {

    private final HallRepository hallRepository;

    public HallService(
        HallRepository hallRepository
    ){
        this.hallRepository = hallRepository;
    }

    public Hall register(Long id, String name){

        if(hallRepository.existsById(id)){
            throw new DuplicateHallException(id);
        }

        Hall hall = new Hall(id, name);

        return hallRepository.save(hall);
    }

    @Transactional(readOnly = true)
    public int countSeats(Long hallId){

        Hall hall = hallRepository.findById(hallId)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    "존재하지 않는 공연장입니다."
                )
            );

        return hall.getSeats().size();
    }

    @Transactional(readOnly = true)
    public void printAllHallSeatCounts(){

        List<Hall> halls = hallRepository.findAllWithSeats();

        for(Hall hall : halls){
            System.out.println(
                "Hall " + hall.getId() + ": "
                + hall.getSeats().size() + " seats"
            );
        }
    }
}