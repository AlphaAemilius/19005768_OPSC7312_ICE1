package com.vc19005768.weatherv2.fiveDayForecast;

import java.util.List;

public class DailyForecasts
{
    private String Date;

    private int EpochDate;

    private com.vc19005768.weatherv2.fiveDayForecast.Temperature Temperature;

    private com.vc19005768.weatherv2.fiveDayForecast.Day Day;

    private com.vc19005768.weatherv2.fiveDayForecast.Night Night;

    private List<String> Sources;

    private String MobileLink;

    private String Link;

    public void setDate(String Date){
        this.Date = Date;
    }
    public String getDate(){
        return this.Date;
    }
    public void setEpochDate(int EpochDate){
        this.EpochDate = EpochDate;
    }
    public int getEpochDate(){
        return this.EpochDate;
    }
    public void setTemperature(Temperature Temperature){
        this.Temperature = Temperature;
    }
    public Temperature getTemperature(){
        return this.Temperature;
    }
    public void setDay(Day Day){
        this.Day = Day;
    }
    public Day getDay(){
        return this.Day;
    }
    public void setNight(Night Night){
        this.Night = Night;
    }
    public Night getNight(){
        return this.Night;
    }
    public void setSources(List<String> Sources){
        this.Sources = Sources;
    }
    public List<String> getSources(){
        return this.Sources;
    }
    public void setMobileLink(String MobileLink){
        this.MobileLink = MobileLink;
    }
    public String getMobileLink(){
        return this.MobileLink;
    }
    public void setLink(String Link){
        this.Link = Link;
    }
    public String getLink(){
        return this.Link;
    }
}

