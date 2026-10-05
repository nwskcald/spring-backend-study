package com.example.backend.dto;

import com.example.backend.domain.Seat;

public class SeatResponse{

    private String id;
    private String section;
    private boolean reserved;

    public SeatResponse(String id, String section, boolean reserved){
        this.id = id;
        this.section = section;
        this.reserved = reserved;
    }

    public static SeatResponse from(Seat seat){

        return new SeatResponse(
            seat.getId(),
            seat.getSection(),
            seat.isReserved()
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
}