package com.example.backend.controller;

import com.example.backend.domain.Hall;
import com.example.backend.dto.HallCreateRequest;
import com.example.backend.service.HallService;
import com.example.backend.dto.HallResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/halls")
public class HallController{

    private final HallService hallService;

    public HallController(
        HallService hallService
    ){
        this.hallService = hallService;
    }

    @PostMapping
    public ResponseEntity<HallResponse> register(
        @RequestBody HallCreateRequest request
    ){
        Hall hall = hallService.register(
            request.getId(),
            request.getName()
        );

        HallResponse hallResponse = HallResponse.from(hall);

        return ResponseEntity.status(201).body(hallResponse);
    }

    @GetMapping("/{id}/seats/count")
    public ResponseEntity<Integer> countSeats(
        @PathVariable Long id
    ){
        int count = hallService.countSeats(id);

        return ResponseEntity.status(200).body(count);
    }

    @GetMapping("/debug/seat-counts")
    public ResponseEntity<Void> printAllHallSeatCounts(){

        hallService.printAllHallSeatCounts();

        return ResponseEntity.status(204).build();
    }
}