package com.example.tripschedule;

public class SpeedBus {

    private String startTerminal;
    private String destTerminal;
    private String wasteTime;
    private String fare;
    private String schedule;


    public SpeedBus(String startTerminal,String destTerminal,String wasteTime,String fare,String schedule){
        this.startTerminal=startTerminal;
        this.destTerminal=destTerminal;
        this.wasteTime=wasteTime;
        this.fare=fare;
        this.schedule=schedule;
    }
    public String getStartTerminal() {
        return startTerminal;
    }

    public void setStartTerminal(String startTerminal) {
        this.startTerminal = startTerminal;
    }

    public String getDestTerminal() {
        return destTerminal;
    }

    public void setDestTerminal(String destTerminal) {
        this.destTerminal = destTerminal;
    }

    public String getWasteTime() {
        return wasteTime;
    }

    public void setWasteTime(String wasteTime) {
        this.wasteTime = wasteTime;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }


    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }









}
