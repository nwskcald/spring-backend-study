package com.example.backend.dto;

public class SeatSectionUpdateRequest{

    private String section;

    public SeatSectionUpdateRequest(String section){
        this.section = section;
    }

    public SeatSectionUpdateRequest(){
    }

    public String getSection(){
        return section;
    }

    public void setSection(String section){
        this.section = section;
    }
}