package com.example.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Seat{

    @Id
    private String id;
    private String section;
    private boolean reserved;

    public Seat(String id, String section){
        this.id = id;
        this.section = section;
        this.reserved = false;
    }

    protected Seat(){
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