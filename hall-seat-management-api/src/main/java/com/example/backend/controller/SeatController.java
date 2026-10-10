package com.example.backend.controller;

import java.util.List;
import com.example.backend.domain.Seat;
import com.example.backend.dto.SeatCreateRequest;
import com.example.backend.dto.SeatResponse;
import com.example.backend.service.SeatService;
import com.example.backend.dto.SeatSectionUpdateRequest;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/{id}/reserve-test")
    public ResponseEntity<Void> reserveWithFailure(
        @PathVariable String id
    ){
        seatService.reserveWithFailure(id);

        return ResponseEntity.status(204).build();
    }

    @PostMapping("/{id}/reserve")
    public ResponseEntity<Void> reserveSuccessfully(
        @PathVariable String id
    ){
        seatService.reserveSuccessfully(id);

        return ResponseEntity.status(204).build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<SeatResponse>> findAvailableSeats(){

        List<Seat> seats = seatService.findAvailableSeats();

        List<SeatResponse> seatResponses = seats.stream()
            .map(seat -> SeatResponse.from(seat))
            .toList();
        
        return ResponseEntity.status(200).body(seatResponses);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SeatResponse>> searchAvailableSeats(
        @RequestParam String section
    ){

        List<Seat> seats = 
            seatService.findAvailableSeatsBySection(section);

        List<SeatResponse> seatResponses = seats.stream()
            .map(seat -> SeatResponse.from(seat))
            .toList();
        
        return ResponseEntity.status(200).body(seatResponses);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<SeatResponse>> findAvailableSeatsPaged(
        @RequestParam int page,
        @RequestParam int size
    ){

        Page<Seat> seats =
            seatService.findAvailableSeatsPaged(page, size);

        Page<SeatResponse> seatResponses =
            seats.map(seat -> SeatResponse.from(seat));

        return ResponseEntity.status(200).body(seatResponses);
    }

    @GetMapping("/search/page")
    public ResponseEntity<Page<SeatResponse>> findAvailableSeatsBySectionPaged(
        @RequestParam String section,
        @RequestParam int page,
        @RequestParam int size
    ){

        Page<Seat> seats =
            seatService.findAvailableSeatsBySectionPaged(section, page, size);

        Page<SeatResponse> seatResponses = 
            seats.map(seat -> SeatResponse.from(seat));

        return ResponseEntity.status(200).body(seatResponses);
    }
}