package com.example.fleet.exception;

public class TaskAlreadyAssignedException
    extends RuntimeException{

        public TaskAlreadyAssignedException(){
            super("이미 배정된 작업입니다.");
        }
    }