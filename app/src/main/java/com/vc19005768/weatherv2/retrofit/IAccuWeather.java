package com.vc19005768.weatherv2.retrofit;



import com.vc19005768.weatherv2.FiveDayForecast;
import com.vc19005768.weatherv2.currentForecast.CurrentWeather;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IAccuWeather {

    @GET("forecasts/v1/daily/5day/{locationKey}")
    Observable<FiveDayForecast> getFiveDayForecast(
            @Path("locationKey") String locationKey,
            @Query("apikey") String apiKey,
            @Query("metric") boolean metric);

    @GET("currentconditions/v1/{locationKey}")
    Observable<List<CurrentWeather>> getCurrentConditions(
            @Path("locationKey") String locationKey,
            @Query("apikey") String apiKey);


}
