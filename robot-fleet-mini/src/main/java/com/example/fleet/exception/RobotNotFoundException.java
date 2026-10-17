package com.example.fleet.exception;

public class RobotNotFoundException
    extends RuntimeException{

        public RobotNotFoundException(){
            super("등록되지 않은 로봇입니다.");
        }
    }