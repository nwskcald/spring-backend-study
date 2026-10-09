package com.example.backend.dto;

public class HallCreateRequest{

    private Long id;
    private String name;

    public HallCreateRequest(){
    }

    public HallCreateRequest(
        Long id,
        String name
    ){
        this.id = id;
        this.name = name;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }
}