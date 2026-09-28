package com.example.fleet.alert;

import org.springframework.stereotype.Repository;

@Repository
public class SlackAlertSender implements AlertSender{

    @Override
    public void send(String robotName){
        System.out.println("Slack 알림 전송: " + robotName);
    }
}