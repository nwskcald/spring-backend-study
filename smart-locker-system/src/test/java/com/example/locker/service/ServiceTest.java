package com.example.locker.service;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

import com.example.locker.domain.Locker;
import com.example.locker.domain.LockerSize;
import com.example.locker.domain.LockerStatus;
import com.example.locker.domain.Storage;
import com.example.locker.exception.DuplicateLockerException;
import com.example.locker.exception.LockerNotFoundException;
import com.example.locker.repository.LockerRepository;
import com.example.locker.repository.StorageRepository;

@ExtendWith(MockitoExtension.class)
class ServiceTest{

    @Mock
    private LockerRepository lockerRepository;

    @Mock 
    private StorageRepository storageRepository;

    @InjectMocks 
    private LockerService lockerService;

    @Test 
    void lockerRegister_success(){

        // Given
        when(lockerRepository.existsByNumber("L-001"))
            .thenReturn(false);

        // When
        lockerService.register(
            "L-001", "1F-A_01", LockerSize.MEDIUM
        );

        // Then
        verify(lockerRepository).existsByNumber("L-001");
        verify(lockerRepository).save(any(Locker.class));
    }

    @Test
    void lockerRegister_throwDuplicateLockerException(){

        // Given
        when(lockerRepository.existsByNumber("L-001"))
            .thenReturn(true);

        // When & Then
        assertThrows(
            DuplicateLockerException.class,
            () -> lockerService.register(
                "L-001", "1F_A_01", LockerSize.MEDIUM
            )
        );

        verify(lockerRepository, never()).save(any(Locker.class));
    }

    @Test
    void lockerOccupy_success(){

        // Given
        Locker locker = new Locker(
            "L-001",
            "1F-A-01",
            LockerSize.MEDIUM
        );

        when(lockerRepository.findByNumber("L-001"))
            .thenReturn(Optional.of(locker));

        // When
        lockerService.occupy("L-001", "Peter");

        // Then
        verify(lockerRepository).findByNumber("L-001");
        assertEquals(LockerStatus.OCCUPIED, locker.getStatus());
        verify(storageRepository).save(any(Storage.class));
    }

    @Test
    void occupyLocker_throwLockerNotFoundException(){

        // Given
        when(lockerRepository.findByNumber("L-001"))
            .thenReturn(Optional.empty());

        // Then
        assertThrows(
            LockerNotFoundException.class,
            () -> lockerService.occupy("L-001", "Peter")
        );

        verify(storageRepository, never()).save(any(Storage.class));
    }
}