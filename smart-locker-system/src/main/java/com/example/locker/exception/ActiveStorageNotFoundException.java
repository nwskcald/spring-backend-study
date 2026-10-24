package com.example.locker.exception;

public class ActiveStorageNotFoundException extends RuntimeException{
    
    public ActiveStorageNotFoundException(){
        super("활성화된 Locker 기록을 찾을 수 없습니다");
    }
}