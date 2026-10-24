package com.example.locker.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import com.example.locker.exception.EndCompletedStorageException;

@Entity
public class Storage{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordNumber;

    private String userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private StorageStatus status;

    @ManyToOne
    @JoinColumn(name = "locker_number", nullable = false)
    private Locker locker;

    // Storage는 Locker의 이용기록
    // Locker의 사용이 시작되면 Storage 생성자 호출 
    public Storage(
        Locker locker,
        String userId
    ){
        this.locker = locker;
        this.userId = userId;
        this.status = StorageStatus.ACTIVE;
        this.startTime = LocalDateTime.now();
    }

    protected Storage(){
    }

    public Long getRecordNumber(){
        return recordNumber;
    }

    public String getUserId(){
        return userId;
    }

    public LocalDateTime getStartTime(){
        return startTime;
    }

    public LocalDateTime getEndTime(){
        return endTime;
    }

    public StorageStatus getStatus(){
        return status;
    }

    public Locker getLocker(){
        return locker;
    }

    private void updateStatusCompleted(){
        this.status = StorageStatus.COMPLETED;
        this.endTime = LocalDateTime.now();
    }

    public void end(){
        if(!(this.status == StorageStatus.ACTIVE)){
            throw new EndCompletedStorageException();
        }
        
        updateStatusCompleted();
    }
}