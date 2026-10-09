package com.example.backend.exception;

public class DuplicateSeatException extends RuntimeException{

    public DuplicateSeatException(String id){
        super("Seat already exists: " + id);
    }
}