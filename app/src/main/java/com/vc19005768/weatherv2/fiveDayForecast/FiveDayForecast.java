package com.vc19005768.weatherv2.fiveDayForecast;

import com.vc19005768.weatherv2.fiveDayForecast.DailyForecasts;
import com.vc19005768.weatherv2.fiveDayForecast.Headline;

import java.util.List;

public class FiveDayForecast
{
    private com.vc19005768.weatherv2.fiveDayForecast.Headline Headline;

    private List<com.vc19005768.weatherv2.fiveDayForecast.DailyForecasts> DailyForecasts;

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
