package com.example.locker.exception;

public class EndCompletedStorageException extends RuntimeException{
    
    public EndCompletedStorageException(){
        super("이미 종료된 Storage 입니다.");
    }
}