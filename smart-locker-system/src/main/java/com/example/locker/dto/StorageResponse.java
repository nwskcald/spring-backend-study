package com.example.locker.dto;

import java.time.LocalDateTime;

import com.example.locker.domain.*;
import com.example.locker.domain.StorageStatus;

public class StorageResponse{

    private Long recordNumber;
    private String lockerNumber;
    private String userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private StorageStatus status;

    public StorageResponse(
        Long recordNumber,
        String lockerNumber,
        String userId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        StorageStatus status
    ){
        this.recordNumber = recordNumber;
        this.lockerNumber = lockerNumber;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public StorageResponse(){
    }

    public static StorageResponse from(Storage storage){
        return new StorageResponse(
            storage.getRecordNumber(),
            storage.getLocker().getNumber(),
            storage.getUserId(),
            storage.getStartTime(),
            storage.getEndTime(),
            storage.getStatus()
        );
    }

    public Long getRecordNumber(){
        return recordNumber;
    }

    public String getLockerNumber(){
        return lockerNumber;
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

    public StorageStatus getStorageStatus(){
        return status;
    }

    public void setRecordNumber(Long recordNumber){
        this.recordNumber = recordNumber;
    }

    public void setLockerNumber(String lockerNumber){
        this.lockerNumber = lockerNumber;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setStartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime){
        this.endTime = endTime;
    }

    public void setStorageStatus(StorageStatus status){
        this.status = status;
    }
}
