package com.example.locker.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Comparator;

import com.example.locker.domain.Locker;
import com.example.locker.domain.Storage;
import com.example.locker.domain.LockerSize;
import com.example.locker.domain.StorageStatus;
import com.example.locker.repository.LockerRepository;
import com.example.locker.repository.StorageRepository;
import com.example.locker.exception.LockerNotFoundException;
import com.example.locker.exception.DuplicateLockerException;
import com.example.locker.exception.ActiveStorageNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LockerService{

    private final LockerRepository lockerRepository;
    private final StorageRepository storageRepository;

    public LockerService(
        LockerRepository lockerRepository, 
        StorageRepository storageRepository
    ){
        this.lockerRepository = lockerRepository;
        this.storageRepository = storageRepository;
    }

    @Transactional
    public Locker register(String number, String location, LockerSize size){
        
        if(lockerRepository.existsByNumber(number)){
            throw new DuplicateLockerException();
        }

        Locker locker = new Locker(number, location, size);

        return lockerRepository.save(locker);
    }

    @Transactional(readOnly = true)
    public List<Locker> getLockerList(){
        
        // JpaRepository가 제공하는 findAll() 메서드의 반환 타입
        // List<T>
        List<Locker> lockers = lockerRepository.findAll()
            .stream()
            .sorted(Comparator.comparing(locker -> locker.getNumber()))
            .toList();

        return lockers; 
    }


    @Transactional
    public Storage occupy(String lockerNumber, String userId){

        Locker locker = lockerRepository.findByNumber(lockerNumber)
            .orElseThrow(
                () -> new LockerNotFoundException()
            );

        locker.occupy();

        Storage storage = new Storage(locker, userId);
        storageRepository.save(storage);

        return storage;
    }

    @Transactional
    public Storage terminate(String lockerNumber){

        Locker locker = lockerRepository.findByNumber(lockerNumber)
            .orElseThrow(
                () -> new LockerNotFoundException()
            );

        locker.terminate();

        Storage storage = 
            storageRepository.findByLockerAndStatus(locker, StorageStatus.ACTIVE)
                .orElseThrow(
                    () -> new ActiveStorageNotFoundException()
                );

        storage.end();

        return storage;
    }
}