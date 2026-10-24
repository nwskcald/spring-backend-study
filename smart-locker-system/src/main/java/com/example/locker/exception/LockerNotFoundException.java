package com.example.locker.exception;

public class LockerNotFoundException extends RuntimeException{
    
    public LockerNotFoundException(){
        super("등록되지 않은 Locker 입니다.");
    }
}