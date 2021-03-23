package com.vc19005768.weatherv2;

import java.util.List;

public class FiveDayForecast
{
    private Headline Headline;

    private List<DailyForecasts> DailyForecasts;

    public void setHeadline(Headline Headline){
        this.Headline = Headline;
    }
    public Headline getHeadline(){
        return this.Headline;
    }
    public void setDailyForecasts(List<DailyForecasts> DailyForecasts){
        this.DailyForecasts = DailyForecasts;
    }
    public List<DailyForecasts> getDailyForecasts(){
        return this.DailyForecasts;
    }
}
