package com.example.locker.dto;

import com.example.locker.domain.Locker;
import com.example.locker.domain.LockerSize;
import com.example.locker.domain.LockerStatus;

public class LockerResponse{

    private String number;
    private String location;
    private LockerSize size;
    private LockerStatus status;

    public LockerResponse(
        String number,
        String location,
        LockerSize size,
        LockerStatus status
    ){
        this.number = number;
        this.location = location;
        this.size = size;
        this.status = status;
    }

    public LockerResponse(){
    }

    public static LockerResponse from(Locker locker){
        return new LockerResponse(
            locker.getNumber(),
            locker.getLocation(),
            locker.getSize(),
            locker.getStatus()
        );
    }

    public String getNumber(){
        return number;
    }

    public String getLocation(){
        return location;
    }

    public LockerSize getLockerSize(){
        return size;
    }

    public LockerStatus getLockerStatus(){
        return status;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setLockerSize(LockerSize size){
        this.size = size;
    }

    public void setLockerStatus(LockerStatus status){
        this.status = status;
    }
}