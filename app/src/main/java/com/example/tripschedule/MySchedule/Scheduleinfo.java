package com.example.tripschedule.MySchedule;

import com.example.tripschedule.SelectLocation.SelectItem;

import java.util.ArrayList;

public class Scheduleinfo {
    private ArrayList<SelectItem> plan;
    private String publisher;
    public Scheduleinfo(ArrayList<SelectItem> plan,String publisher) {
        this.plan=plan;
        this.publisher=publisher;

    }


    public ArrayList<SelectItem> getPlan() {
        return plan;
    }

    public void setPlan(ArrayList<SelectItem> plan) {
        this.plan = plan;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


}


