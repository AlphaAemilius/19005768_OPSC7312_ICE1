package com.vc19005768.weatherv2.location;

import com.vc19005768.weatherv2.fiveDayForecast.DailyForecasts;

import java.util.List;

public class Cities {
    private List<AccuWeatherLocation> cityObjs;
    public List<AccuWeatherLocation> getCityObjs() {
        return cityObjs;
    }

    public void setCityObjs(List<AccuWeatherLocation> cityObjs) {
        this.cityObjs = cityObjs;
    }


}
