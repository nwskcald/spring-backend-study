package com.example.locker.controller;

import com.example.locker.service.StorageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.locker.domain.Storage;
import com.example.locker.dto.*;

@RestController
@RequestMapping("/storages")
public class StorageController{

    private StorageService storageService;

    public StorageController(
        StorageService storageService
    ){
        this.storageService = storageService;
    }

    @GetMapping("/{recordNumber}")
    public ResponseEntity<StorageResponse> findByRecordNumber(
        @PathVariable Long recordNumber
    ){
        Storage storage = 
            storageService.findByRecordNumber(recordNumber);

        StorageResponse response = StorageResponse.from(storage);

        return ResponseEntity.status(200).body(response);
    }
}