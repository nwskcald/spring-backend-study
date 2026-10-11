package com.example.backend.dto;

import com.example.backend.domain.Seat;

public class SeatResponse{

    private String id;
    private String section;
    private boolean reserved;
    private Long hallId;
    private String hallName;

    public SeatResponse(String id, String section, 
    boolean reserved, Long hallId, String hallName){
        this.id = id;
        this.section = section;
        this.reserved = reserved;
        this.hallId = hallId;
        this.hallName = hallName;
    }

    public static SeatResponse from(Seat seat){

        return new SeatResponse(
            seat.getId(),
            seat.getSection(),
            seat.isReserved(),
            seat.getHall().getId(),
            seat.getHall().getName()
        );
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
    
    public Long getHallId(){
        return hallId;
    }

    public String getHallName(){
        return hallName;
    }
}