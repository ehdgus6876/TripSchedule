package com.example.tripschedule.Transport;

public class TrainItem {

    String startStationID;
    String endStationID;
    String trainClass;
    String departureTime;
    String arrivalTime;
    String wasteTime;
    String runDay;
    String Fare;

    public TrainItem(String startStationID,String endStationID,String trainClass,String departureTime,String arrivalTime,String wasteTime,String Fare){
        this.startStationID=startStationID;
        this.endStationID=endStationID;
        this.trainClass=trainClass;
        this.departureTime=departureTime;
        this.arrivalTime=arrivalTime;
        this.wasteTime=wasteTime;
        this.Fare=Fare;

    }

    public String getWasteTime() {
        return wasteTime;
    }

    public void setWasteTime(String wasteTime) {
        this.wasteTime = wasteTime;
    }


    public String getStartStationID() {
        return startStationID;
    }

    public void setStartStationID(String startStationID) {
        this.startStationID = startStationID;
    }

    public String getEndStationID() {
        return endStationID;
    }

    public void setEndStationID(String endStationID) {
        this.endStationID = endStationID;
    }

    public String getTrainClass() {
        return trainClass;
    }

    public void setTrainClass(String tarinClass) {
        this.trainClass = tarinClass;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getRunDay() {
        return runDay;
    }

    public void setRunDay(String runDay) {
        this.runDay = runDay;
    }

    public String getFare() {
        return Fare;
    }

    public void setFare(String fare) {
        Fare = fare;
    }



}
