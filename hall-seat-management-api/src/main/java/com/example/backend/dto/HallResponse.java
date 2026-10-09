package com.example.backend.dto;

import com.example.backend.domain.Hall;

public class HallResponse{

    private Long id;
    private String name;

    public HallResponse(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public static HallResponse from(Hall hall){

        return new HallResponse(
            hall.getId(),
            hall.getName()
        );
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}