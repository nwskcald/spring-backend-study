package com.example.fleet.exception;

public class TaskNotAssignedException
    extends RuntimeException{

        public TaskNotAssignedException(){
            super("로봇을 배정받지 않은 작업입니다.");
        }
    }