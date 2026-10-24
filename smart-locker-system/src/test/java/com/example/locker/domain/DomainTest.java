package com.example.locker.domain;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import com.example.locker.exception.AlreadyOccupiedLockerException;
import com.example.locker.exception.EndCompletedStorageException;

import static org.junit.jupiter.api.Assertions.*;

class DomainTest{

    @Test
    void lockerRegister_success(){

        // Given
        Locker locker = new Locker(
            "L-001",
            "1F-A-01",
            LockerSize.MEDIUM
        );

        // Then
        assertEquals(LockerStatus.AVAILABLE, locker.getStatus());
        assertEquals("L-001", locker.getNumber());
        assertEquals("1F-A-01", locker.getLocation());
        assertEquals(LockerSize.MEDIUM, locker.getSize());
    }

    @Test 
    void lockerOccupy_AVAILABLE_success(){

        // Given
        Locker locker = new Locker(
            "L-001",
            "1F-A-01",
            LockerSize.MEDIUM
        );

        // When
        locker.occupy();

        // Then
        assertEquals(LockerStatus.OCCUPIED, locker.getStatus());
    }

    @Test 
    void lockerOccupy_OCCUPIED_throwAlreadyOccupiedLockerException(){

        // Given
        Locker locker = new Locker(
            "L-001",
            "1F-A-01",
            LockerSize.MEDIUM
        );

        locker.occupy();

        // When & Then
        assertThrows(
            AlreadyOccupiedLockerException.class,
            () -> locker.occupy()
        );

        assertEquals(LockerStatus.OCCUPIED, locker.getStatus());
    }

    @Test 
    void storageEnd_ACTIVE_success(){

        // Given
        Locker locker = new Locker(
            "L-001",
            "1F-A-01",
            LockerSize.MEDIUM
        );

        locker.occupy();

        Storage storage = new Storage(
            locker,
            "Peter"
        );

        LocalDateTime startTime = storage.getStartTime();

        // When
        storage.end();

        // Then
        assertEquals(StorageStatus.COMPLETED, storage.getStatus());
        assertEquals(startTime, storage.getStartTime());
        assertNotNull(storage.getEndTime());
    }

    @Test
    void storageEnd_COMPLETED_throwEndCompletedStorageException(){

        // Given
        Locker locker = new Locker(
            "L-001",
            "1F-A-01",
            LockerSize.MEDIUM
        );

        locker.occupy();

        Storage storage = new Storage(
            locker,
            "Peter"
        );

        storage.end();
        LocalDateTime endTime = storage.getEndTime();

        // When & Then
        assertThrows(
            EndCompletedStorageException.class,
            () -> storage.end()
        );

        assertEquals(endTime, storage.getEndTime());
    }
}