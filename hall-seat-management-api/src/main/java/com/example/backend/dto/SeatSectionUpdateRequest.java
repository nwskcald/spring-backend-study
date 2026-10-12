package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class SeatSectionUpdateRequest{

    @NotBlank (message = "구역은 필수입니다.")
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