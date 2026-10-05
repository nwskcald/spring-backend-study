package com.example.backend.domain;

public class Seat{

    private String id;
    private String section;
    private boolean reserved;

    public Seat(String id, String section){
        this.id = id;
        this.section = section;
        this.reserved = false;
    }

    public String getId(){
        return id;
    }

    public String getSection(){
        return section;
    }

    public boolean isReserved(){
        return reserved;
    }
}