package com.example.locker.dto;

import jakarta.validation.constraints.NotBlank;

public class OccupyRequest{

    @NotBlank
    private String userId;

    public OccupyRequest(String userId){
        this.userId = userId;
    }

    public OccupyRequest(){
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }
}