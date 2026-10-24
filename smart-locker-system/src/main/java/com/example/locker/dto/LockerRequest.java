package com.example.locker.dto;

import com.example.locker.domain.LockerSize;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LockerRequest{

    @NotBlank 
    private String number;

    @NotBlank 
    private String location;

    @NotNull
    private LockerSize size;

    public LockerRequest(
        String number,
        String location,
        LockerSize size
    ){
        this.number = number;
        this.location = location;
        this.size = size;
    }

    public LockerRequest(){
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

    public void setNumber(String number){
        this.number = number;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setLockerSize(LockerSize size){
        this.size = size;
    }
}