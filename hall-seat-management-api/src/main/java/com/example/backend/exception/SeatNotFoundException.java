package com.example.backend.exception;

public class SeatNotFoundException extends RuntimeException{

    public SeatNotFoundException(String id){
        super("Seat not found: " + id);
    }
}