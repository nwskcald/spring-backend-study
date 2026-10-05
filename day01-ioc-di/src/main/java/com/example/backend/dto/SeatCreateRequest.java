package com.example.backend.dto;

public class SeatCreateRequest{

    private String id;
    private String section;

    public SeatCreateRequest(String id, String section){
        this.id = id;
        this.section = section;
    }

    public String getId(){
        return id;
    }

    public String getSection(){
        return section;
    }
}