package com.example.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Hall{

    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "hall")
    private List<Seat> seats = new ArrayList<>();

    public Hall(Long id, String name){
        this.id = id;
        this.name = name;
    }

    protected Hall(){
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public List<Seat> getSeats(){
        return seats;
    }
}