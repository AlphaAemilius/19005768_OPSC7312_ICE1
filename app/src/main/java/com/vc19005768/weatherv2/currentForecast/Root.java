package com.vc19005768.weatherv2.currentForecast;

public class Root
{
    private CurrentWeather currentWeather;

    public void setCurrentWeather(CurrentWeather det){
        this.currentWeather = det;
    }
    public CurrentWeather getCurrentWeather(){
    return this.currentWeather;
}
}