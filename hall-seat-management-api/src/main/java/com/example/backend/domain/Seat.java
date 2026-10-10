package com.example.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Seat{

    @Id
    private String id;
    private String section;
    private boolean reserved;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    public Seat(String id, String section, Hall hall){

        if(hall == null){
            throw new IllegalArgumentException("Hall은 필수입니다.");
        }
        this.id = id;
        this.section = section;
        this.reserved = false;
        this.hall = hall;

        hall.getSeats().add(this);
    }

    protected Seat(){
    }

    public String getId(){
        return id;
    }

    public String getSection(){
        return section;
    }

    public Hall getHall(){
        return hall;
    }

    public boolean isReserved(){
        return reserved;
    }

    public void changeSection(String newSection){
        this.section = newSection;
    }

    public void reserve(){
        if(isReserved()){
            throw new IllegalStateException(
                "이미 예약된 좌석입니다."
            );
        }

        this.reserved = true;
    }
}