package com.example.backend.controller;

import com.example.backend.domain.Seat;
import com.example.backend.dto.SeatCreateRequest;
import com.example.backend.dto.SeatResponse;
import com.example.backend.service.SeatService;
import com.example.backend.dto.SeatSectionUpdateRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seats")
public class SeatController{

    private final SeatService seatService;

    public SeatController(SeatService seatService){
        this.seatService = seatService;
    }

    @PostMapping
    public ResponseEntity<SeatResponse> register(
        @RequestBody SeatCreateRequest request
    ){
        Seat seat = seatService.register(
            request.getId(),
            request.getSection(),
            request.getHallId()
        );

        SeatResponse seatResponse = SeatResponse.from(seat);

        return ResponseEntity.status(201).body(seatResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatResponse> findById(
        @PathVariable String id
    ){
        Seat seat = seatService.findById(id);

        SeatResponse seatResponse = SeatResponse.from(seat);

        return ResponseEntity.status(200).body(seatResponse);
    }

    @PatchMapping("/{id}/section")
    public ResponseEntity<SeatResponse> changeSection(
        @PathVariable String id,
        @RequestBody SeatSectionUpdateRequest request
    ){
        Seat seat = seatService.changeSection(id, request.getSection());

        SeatResponse seatResponse = SeatResponse.from(seat);

        return ResponseEntity.status(200).body(seatResponse);
    }
}