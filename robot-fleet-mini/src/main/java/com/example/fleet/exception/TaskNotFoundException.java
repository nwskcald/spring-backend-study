package com.example.fleet.exception;

public class TaskNotFoundException
    extends RuntimeException{

        public TaskNotFoundException(){
            super("등록되지 않은 작업입니다");
        }
    }