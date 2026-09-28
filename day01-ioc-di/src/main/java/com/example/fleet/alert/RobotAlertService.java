package com.example.fleet.alert;

import com.example.fleet.alert.AlertSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RobotAlertService{

    private final AlertSender alertSender;
    public final String robotName = "R-07";

    public RobotAlertService(
        @Qualifier("slackAlertSender") AlertSender alertSender
    ){
        this.alertSender = alertSender;
    }

    public void reportFailure(String robotName){
        System.out.println("로봇 장애 발생: R-07");
        alertSender.send("R-07");
    }
}