package com.example.locker.repository;

import com.example.locker.domain.Locker;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LockerRepository extends JpaRepository<Locker, String>{

    public boolean existsByNumber(String number);

    Optional<Locker> findByNumber(String number);
}