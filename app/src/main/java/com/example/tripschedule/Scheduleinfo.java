package com.example.tripschedule;

public class Scheduleinfo {
    private String schedule;
    private String publisher;
    public Scheduleinfo(String schedule,String publisher) {
        this.schedule = schedule;
        this.publisher=publisher;

    }


    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getSchedule() {
        return schedule;
    }



    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


}


