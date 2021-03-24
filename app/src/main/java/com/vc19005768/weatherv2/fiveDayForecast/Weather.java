package com.vc19005768.weatherv2.fiveDayForecast;

import com.vc19005768.weatherv2.fiveDayForecast.DailyForecasts;
import com.vc19005768.weatherv2.fiveDayForecast.Headline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Weather {


    private Headline headline;

    private List<DailyForecasts> dailyForecasts = null;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public Headline getHeadline() {
        return headline;
    }


    public void setHeadline(Headline headline) {
        this.headline = headline;
    }


    public List<DailyForecasts> getDailyForecasts() {
        return dailyForecasts;
    }


    public void setDailyForecasts(List<DailyForecasts> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }


    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
