package com.example.tripschedule;

public class Weather {
    String date;
    String weather;
    String lowTemp;
    String highTemp;

    public Weather(String date,String weather,String lowTemp,String highTemp){
        this.date=date;
        this.weather=weather;
        this.lowTemp=lowTemp;
        this.highTemp=highTemp;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp;
    }



}
