package com.example.fleet.exception;

public class RobotNotAssignedException
    extends RuntimeException{

        public RobotNotAssignedException(){
            super("작업을 배정받지 않은 로봇입니다.");
        }
    }