package com.example.locker.exception;

public class AlreadyOccupiedLockerException extends RuntimeException{
    
    public AlreadyOccupiedLockerException(){
        super("이용중인 Locker 입니다.");
    }
}