package com.vc19005768.weatherv2.retrofit;



import com.vc19005768.weatherv2.fiveDayForecast.FiveDayForecast;
import com.vc19005768.weatherv2.currentForecast.CurrentWeather;
import com.vc19005768.weatherv2.location.AccuWeatherLocation;


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

    @GET("locations/v1/cities/geoposition/search")
    Observable<AccuWeatherLocation> getLocationByPosition(
            @Query("q") String geoposition,
            @Query("apikey") String apiKey);


}
