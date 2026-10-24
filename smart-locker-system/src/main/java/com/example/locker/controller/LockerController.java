package com.example.locker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.locker.domain.*;
import com.example.locker.service.LockerService;

import jakarta.validation.Valid;

import com.example.locker.dto.*;
import java.util.List;

@RestController
@RequestMapping("/lockers")
public class LockerController{

    private LockerService lockerService;

    public LockerController(
        LockerService lockerService
    ){
        this.lockerService = lockerService;
    }

    @PostMapping
    public ResponseEntity<LockerResponse> register(
        @Valid @RequestBody LockerRequest request
    ){
        Locker locker = 
            lockerService.register(
                request.getNumber(),
                request.getLocation(),
                request.getSize()
            );

        LockerResponse response = LockerResponse.from(locker);

        return ResponseEntity.status(201).body(response);
    }


    @GetMapping
    public ResponseEntity<List<LockerResponse>> getLockerList(){

        List<LockerResponse> responses = lockerService.getLockerList()
            .stream()
            .map(locker -> LockerResponse.from(locker))
            .toList();

        return ResponseEntity.status(200).body(responses);
    }


    @PostMapping("/{lockerNumber}/occupy")
    public ResponseEntity<StorageResponse> occupy(
        @PathVariable String lockerNumber,
        @Valid @RequestBody OccupyRequest request
    ){
        Storage storage =
            lockerService.occupy(lockerNumber, request.getUserId());

        StorageResponse response = StorageResponse.from(storage);

        return ResponseEntity.status(201).body(response);
    }

    @PatchMapping("/{lockerNumber}/terminate")
    public ResponseEntity<StorageResponse> terminate(
        @PathVariable String lockerNumber
    ){
        Storage storage = lockerService.terminate(lockerNumber);

        StorageResponse response = StorageResponse.from(storage);

        return ResponseEntity.status(200).body(response);
    }
}