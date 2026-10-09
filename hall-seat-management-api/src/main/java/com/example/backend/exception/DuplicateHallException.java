package com.example.backend.exception;

public class DuplicateHallException extends RuntimeException{

    public DuplicateHallException(Long id){
        super("Hall already exists: " + id);
    }
}