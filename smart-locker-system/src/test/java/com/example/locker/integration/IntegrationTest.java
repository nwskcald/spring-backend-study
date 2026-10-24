package com.example.locker.integration;

import java.util.List;
import com.example.locker.domain.Locker;
import com.example.locker.domain.LockerSize;
import com.example.locker.domain.LockerStatus;
import com.example.locker.domain.Storage;
import com.example.locker.domain.StorageStatus;
import com.example.locker.exception.AlreadyOccupiedLockerException;
import com.example.locker.repository.LockerRepository;
import com.example.locker.repository.StorageRepository;
import com.example.locker.service.LockerService;

import org.junit.jupiter.api.Test;
import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest 
@Transactional 
public class IntegrationTest{

    @Autowired 
    private EntityManager entityManager;

    @Autowired 
    private LockerRepository lockerRepository;

    @Autowired 
    private StorageRepository storageRepository;

    @Autowired 
    private LockerService lockerService;

    @Test
    void lockerRegister_persistsToDataBase(){

        // When
        lockerService.register("L-001", "1F_A_01", LockerSize.MEDIUM);
        entityManager.flush();
        entityManager.clear();

        Locker locker = lockerRepository.findByNumber("L-001")
            .orElseThrow();

        assertEquals("L-001", locker.getNumber());
        assertEquals("1F_A_01", locker.getLocation());
        assertEquals(LockerSize.MEDIUM, locker.getSize());
        assertEquals(LockerStatus.AVAILABLE, locker.getStatus());
    }

    @Test 
    void getLockerList_success(){

        // Given
        lockerService.register("L-002", "1F_A_02", LockerSize.LARGE);
        entityManager.flush();
        entityManager.clear();

        lockerService.register("L-001", "1F_A_01", LockerSize.MEDIUM);
        entityManager.flush();
        entityManager.clear();

        lockerService.register("L-003", "1F_B_01", LockerSize.SMALL);
        entityManager.flush();
        entityManager.clear();

        // When
        List<Locker> lockers = lockerService.getLockerList();

        assertEquals(3, lockers.size());

        Locker locker_1 = lockers.get(0);
        assertEquals("L-001", locker_1.getNumber());

        Locker locker_2 = lockers.get(1);
        assertEquals("L-002", locker_2.getNumber());

        Locker locker_3 = lockers.get(2);
        assertEquals("L-003", locker_3.getNumber());
    }

    @Test
    void lockerOccupy_persistsToDataBase(){

        // Given & When
        Locker locker = 
            lockerService.register("L-001", "1F_A_01", LockerSize.MEDIUM);

        lockerService.occupy("L-001", "Peter");

        entityManager.flush();
        entityManager.clear();

        // Then
        locker = lockerRepository.findByNumber("L-001")
            .orElseThrow();

        assertEquals(LockerStatus.OCCUPIED, locker.getStatus());

        List<Storage> storages = storageRepository.findAll();

        assertEquals(1, storages.size());

        Storage storage = storages.get(0);

        assertEquals(StorageStatus.ACTIVE, storage.getStatus());
    }

    @Test 
    void lockerTerminate_persistsToDataBase(){

        // Given & When
        Locker locker = 
            lockerService.register("L-001", "1F_A_01", LockerSize.MEDIUM);

        lockerService.occupy("L-001", "Peter");
        lockerService.terminate("L-001");

        entityManager.flush();
        entityManager.clear();

        // Then
        locker = lockerRepository.findByNumber("L-001")
            .orElseThrow();

        assertEquals(LockerStatus.AVAILABLE, locker.getStatus());

        List<Storage> storages = storageRepository.findAll();

        assertEquals(1, storages.size());

        Storage storage = storages.get(0);

        assertEquals(StorageStatus.COMPLETED, storage.getStatus());
        assertNotNull(storage.getEndTime());
    }

    @Test 
    void storageCompleted_persistsToDataBase(){

        // Given & When
        lockerService.register("L-001", "1F_A_01", LockerSize.MEDIUM);

        lockerService.occupy("L-001", "Peter");
        lockerService.terminate("L-001");

        lockerService.occupy("L-001", "James");

        entityManager.flush();
        entityManager.clear();

        // Then
        List<Storage> storages = storageRepository.findAll();

        Storage peterStorage = storages.stream()
            .filter(s -> s.getUserId().equals("Peter"))
            .findFirst()
            .orElseThrow();
        
        Storage jamesStorage = storages.stream()
            .filter(s -> s.getUserId().equals("James"))
            .findFirst()
            .orElseThrow();

        assertEquals(StorageStatus.COMPLETED, peterStorage.getStatus());
        assertEquals(StorageStatus.ACTIVE, jamesStorage.getStatus());
    }

    @Test 
    void occupyLockerOccupied_throwAlreadyOccupiedLockerException(){

        // Given & When
        lockerService.register("L-001", "1F_A_01", LockerSize.MEDIUM);
        lockerService.occupy("L-001", "Peter");

        entityManager.flush();
        entityManager.clear();

        // Then
        assertThrows(
            AlreadyOccupiedLockerException.class,
            () -> lockerService.occupy("L-001", "James")
        );

        List<Storage> storages = storageRepository.findAll();

        assertEquals(1, storages.size());

        Storage storage = storages.get(0);
        assertEquals("Peter", storage.getUserId());
    }
}