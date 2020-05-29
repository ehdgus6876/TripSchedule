package com.example.tripschedule.MySchedule;

import com.example.tripschedule.SelectLocation.SelectItem;

import java.util.ArrayList;

public class Scheduleinfo {
    private ArrayList<SelectItem> plan;
    private String publisher;

    private String Startdate;
    private String Enddate;
    public Scheduleinfo(ArrayList<SelectItem> plan,String publisher,String Startdate,String Enddate) {
        this.plan=plan;
        this.publisher=publisher;
        this.Startdate=Startdate;
        this.Enddate=Enddate;

    }
    public String getStartdate() { return Startdate; }
    public void setStartdate(String startdate) { Startdate = startdate; }
    public String getEnddate() { return Enddate; }
    public void setEnddate(String enddate) { Enddate = enddate; }
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


