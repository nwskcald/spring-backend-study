package com.example.fleet.exception;

public class DuplicateTaskException
    extends RuntimeException{

        public DuplicateTaskException(){
            super("이미 등록된 작업입니다.");
        }
    }