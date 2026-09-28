package com.example.fleet.alert;

import org.springframework.stereotype.Repository;

@Repository
public class ConsoleAlertSender implements AlertSender{

    @Override
    public void send(String robotName){
        System.out.println("Console 알림 전송: " + robotName);
    }
}