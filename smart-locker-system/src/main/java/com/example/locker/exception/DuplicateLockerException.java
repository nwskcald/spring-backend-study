package com.example.locker.exception;

public class DuplicateLockerException extends RuntimeException{
    
    public DuplicateLockerException(){
        super("이미 등록된 Locker 입니다.");
    }
}