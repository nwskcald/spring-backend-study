package com.example.locker.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import com.example.locker.exception.AlreadyOccupiedLockerException;
import com.example.locker.exception.TerminateAvailableLockerException;

@Entity
public class Locker{

    @Id
    private String number;
    private String location;

    @Enumerated(EnumType.STRING)
    private LockerSize size;

    @Enumerated(EnumType.STRING)
    private LockerStatus status;

    public Locker(
        String number,
        String location,
        LockerSize size
    ){
        this.number = number;
        this.location = location;
        this.size = size;
        this.status = LockerStatus.AVAILABLE;
    }

    protected Locker(){
    }

    public String getNumber(){
        return number;
    }

    public String getLocation(){
        return location;
    }

    public LockerSize getSize(){
        return size;
    }

    public LockerStatus getStatus(){
        return status;
    }

    private void updateStatusOccupied(){
        this.status = LockerStatus.OCCUPIED;
    }

    private void updateStatusAvailable(){
        this.status = LockerStatus.AVAILABLE;
    }

    public void occupy(){
        if(!(this.status == LockerStatus.AVAILABLE)){
            throw new AlreadyOccupiedLockerException();
        }

        updateStatusOccupied();
    }

    public void terminate(){
        if(!(this.status == LockerStatus.OCCUPIED)){
            throw new TerminateAvailableLockerException();
        }
        
        updateStatusAvailable();
    }
}