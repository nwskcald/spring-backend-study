package com.example.fleet.exception;

public class RobotAlreadyAssignedException
    extends RuntimeException{

        public RobotAlreadyAssignedException(){
            super("이미 작업 중인 로봇입니다.");
        }
    }