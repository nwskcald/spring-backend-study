package com.example.backend.dto;

public class SeatCreateRequest{

    private String id;
    private String section;
    private Long hallId;

    public SeatCreateRequest(){
    }

    public SeatCreateRequest(String id, String section, Long hallId){
        this.id = id;
        this.section = section;
        this.hallId = hallId;
    }

    public String getId(){
        return id;
    }

    public String getSection(){
        return section;
    }

    public Long getHallId(){
        return hallId;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setSection(String section){
        this.section = section;
    }

    public void setHallId(Long hallId){
        this.hallId = hallId;
    }
}