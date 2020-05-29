package com.example.tripschedule;

public class OnlyDateItem {

    String Startdate;
    String Enddate;
    String id;

    public OnlyDateItem(String Startdate,String Enddate,String id){
        this.Startdate=Startdate;
        this.Enddate=Enddate;
        this.id=id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
