package com.example.locker.service;

import org.springframework.stereotype.Service;

import com.example.locker.domain.Storage;
import com.example.locker.repository.StorageRepository;
import com.example.locker.exception.StorageNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StorageService{

    private final StorageRepository storageRepository;

    public StorageService(
        StorageRepository storageRepository
    ){
        this.storageRepository = storageRepository;
    }

    @Transactional(readOnly = true)
    public Storage findByRecordNumber(Long recordNumber){

        Storage storage = storageRepository.findByRecordNumber(recordNumber)
            .orElseThrow(
                () -> new StorageNotFoundException()
            );

        return storage;
    }
}