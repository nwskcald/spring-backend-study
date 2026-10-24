package com.example.locker.exception;

public class TerminateAvailableLockerException extends RuntimeException{
    
    public TerminateAvailableLockerException(){
        super("이미 활성화된 Locker 입니다.");
    }
}