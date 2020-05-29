package com.example.tripschedule;

public class OnlyDateItem {

    String Startdate;
    String Enddate;

    public OnlyDateItem(String Startdate,String Enddate){
        this.Startdate=Startdate;
        this.Enddate=Enddate;
    }


    public String getStartdate() {
        return Startdate;
    }

    public void setStartdate(String startdate) {
        Startdate = startdate;
    }

    public String getEnddate() {
        return Enddate;
    }

    public void setEnddate(String enddate) {
        Enddate = enddate;
    }

}
