package com.example.fleet.exception;

public class DuplicateRobotException
    extends RuntimeException{

        public DuplicateRobotException(){
            super("이미 등록된 로봇입니다.");
        }
    }