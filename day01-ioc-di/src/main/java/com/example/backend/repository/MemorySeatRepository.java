package com.example.backend.repository;

import com.example.backend.domain.Seat;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemorySeatRepository implements SeatRepository{

    private final Map<String, Seat> store = new HashMap<>();

    @Override
    public void save(Seat seat){

        store.put(seat.getId(), seat);
    }
    
    @Override
    public Optional<Seat> findById(String id){

        return Optional.ofNullable(store.get(id));
    }

    @Override
    public boolean existsById(String id){

        return store.containsKey(id);
    }
}