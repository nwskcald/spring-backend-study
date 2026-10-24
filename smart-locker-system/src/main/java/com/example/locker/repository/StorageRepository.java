package com.example.locker.repository;

import com.example.locker.domain.Locker;
import com.example.locker.domain.Storage;
import com.example.locker.domain.StorageStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, Long>{

    Optional<Storage> findByLockerAndStatus(
        Locker locker,
        StorageStatus status
    );

    Optional<Storage> findByRecordNumber(Long recordNumber);
}