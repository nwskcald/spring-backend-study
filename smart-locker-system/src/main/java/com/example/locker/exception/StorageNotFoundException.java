package com.example.locker.exception;

public class StorageNotFoundException extends RuntimeException{
    
    public StorageNotFoundException(){
        super("등록되지 않은 Storage 입니다");
    }
}