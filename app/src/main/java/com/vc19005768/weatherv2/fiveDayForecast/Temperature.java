package com.vc19005768.weatherv2.fiveDayForecast;

import com.vc19005768.weatherv2.fiveDayForecast.Maximum;
import com.vc19005768.weatherv2.fiveDayForecast.Minimum;

public class Temperature
{
    private com.vc19005768.weatherv2.fiveDayForecast.Minimum Minimum;

    private com.vc19005768.weatherv2.fiveDayForecast.Maximum Maximum;

    public void setMinimum(Minimum Minimum){
        this.Minimum = Minimum;
    }
    public Minimum getMinimum(){
        return this.Minimum;
    }
    public void setMaximum(Maximum Maximum){
        this.Maximum = Maximum;
    }
    public Maximum getMaximum(){
        return this.Maximum;
    }
}