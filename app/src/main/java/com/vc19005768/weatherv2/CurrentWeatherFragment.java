package com.vc19005768.weatherv2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.vc19005768.weatherv2.currentForecast.CurrentWeather;
import com.vc19005768.weatherv2.retrofit.IAccuWeather;
import com.vc19005768.weatherv2.retrofit.RetrofitClient;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class CurrentWeatherFragment extends Fragment {

    private static final String ARG_LOCATION_NAME = "locationName";
    private static final String ARG_LOCATION_KEY = "locationKey";

    private IAccuWeather weatherService;
    private CompositeDisposable compositeDisposable;
    private String locationName;
    private String locationKey;

    public TextView llblCurrent;
    public TextView llblTemp;
    private View view;



    public CurrentWeatherFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getRetrofit();
        weatherService = retrofit.create(IAccuWeather.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }



    public static CurrentWeatherFragment newInstance(String lname, String lkey) { //location things injected here
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LOCATION_NAME, lname);
        args.putString(ARG_LOCATION_KEY, lkey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            locationName = getArguments().getString(ARG_LOCATION_NAME);
            locationKey = getArguments().getString(ARG_LOCATION_KEY);
    }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        compositeDisposable.add(
                weatherService.getCurrentConditions(locationKey,BuildConfig.ACCUWEATHER_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CurrentWeather>>()
                {
                    @Override
                    public void accept(List<CurrentWeather> forecast) {
                        displayData(forecast);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d("MYERROR", "accept: " + throwable.getMessage());
                    }
                }));


        return view;
    }

    private void displayData(List<CurrentWeather> forecast) { //displays data to the fragment from the one element in the list

         llblCurrent = view.findViewById(R.id.lblConditions);
         llblTemp = view.findViewById(R.id.lblTemps);

        llblCurrent.setText("It is " + forecast.get(0).getWeatherText() + " at the moment in " + locationName + ".");
        llblTemp.setText("With a temperature of "+ (int) forecast.get(0).getTemperature().getMetric().getValue() + " °C");

    }

}