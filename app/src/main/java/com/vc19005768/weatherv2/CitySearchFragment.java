package com.vc19005768.weatherv2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mancj.materialsearchbar.MaterialSearchBar;

import com.vc19005768.weatherv2.currentForecast.CurrentWeather;
import com.vc19005768.weatherv2.location.AccuWeatherLocation;

import com.vc19005768.weatherv2.retrofit.IAccuWeather;
import com.vc19005768.weatherv2.retrofit.RetrofitClient;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import com.label305.asynctask.SimpleAsyncTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CitySearchFragment extends Fragment {


    private static final String ARG_LOCATION_NAME = "locationName";
    private static final String ARG_LOCATION_KEY = "searchKey";

    private IAccuWeather weatherService;
    private CompositeDisposable compositeDisposable;
    private String locationName;
    private String searchKey;
    private HashMap<String, String> citiesHashMap;
    private View view;
    public List<AccuWeatherLocation> accu = new ArrayList<>();


    public MaterialSearchBar materialSearchBar;

    public TextView llblSCity;
    public TextView llblSCurrent;
    public TextView llblSWeatherText;



    public CitySearchFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getRetrofit();
        weatherService = retrofit.create(IAccuWeather.class);
        citiesHashMap = new HashMap<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }


    public static CitySearchFragment newInstance(String lname, String skey) {
        CitySearchFragment fragment = new CitySearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LOCATION_NAME, lname);
        args.putString(ARG_LOCATION_KEY, skey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            locationName = getArguments().getString(ARG_LOCATION_NAME);
            searchKey = getArguments().getString(ARG_LOCATION_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_city_search, container, false);
        llblSCity =  view.findViewById(R.id.lblSCity);
        llblSWeatherText =  view.findViewById(R.id.lblSWeatherText);
        llblSCurrent =  view.findViewById(R.id.lblSCurrent);
        materialSearchBar = view.findViewById(R.id.sb_city_name);
        materialSearchBar.setEnabled(false);

        new LoadCities().execute(); //gets data from json file


        return view;
    }

    private class LoadCities extends SimpleAsyncTask<List<AccuWeatherLocation>> {
        public String loadJSON() { //returns cities json
            String json = null;
            try {
                InputStream is = getActivity().getAssets().open("cityData.json");  //gets file
                int size = is.available();                                                  //gets size
                byte[] buffer = new byte[size];                                             // byte array of file size
                is.read(buffer);                                                            // reads data into array
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }

        @Override
        protected List<AccuWeatherLocation> doInBackgroundSimple() {  //cast json list<accuweather>

            Gson gson = new Gson();
            accu = gson.fromJson(loadJSON(),  new TypeToken<List<AccuWeatherLocation>>() {
            }.getType() );
            return accu;
        }

        @Override
        protected void onSuccess(final List<AccuWeatherLocation> cityNames) {
            super.onSuccess(cityNames);
            populateSearchData(cityNames);
        }


    }

    private void populateSearchData(List<AccuWeatherLocation> topCities) {
        citiesHashMap.clear();
        for (AccuWeatherLocation city : topCities) {                    // converts List<AccuWeatherLocations> to HashMap
            citiesHashMap.put(city.getLocalizedName(), city.getKey());
        }
        
        materialSearchBar.setEnabled(true);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggested = new ArrayList<>();
                for (String city : citiesHashMap.keySet())              { // searches hashmap for current dynamic contents of searchbar
                    if (city.toLowerCase().contains(materialSearchBar.getText().toLowerCase())) {
                        suggested.add(city);
                    }
                }
                Collections.sort(suggested);                                //sorts matching locations
                materialSearchBar.setLastSuggestions(suggested);            //sets last suggestions to array(suggested)
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
                    @Override
                    public void onSearchStateChanged(boolean enabled) {}
                    @Override
                    public void onSearchConfirmed(CharSequence text) {
                        Log.d("Search:", text + "");
                        locationName = text.toString();
                        getWeatherByCityName(citiesHashMap.get(locationName)); // calls api requests passing location key from hashmap
                    }
                    @Override
                    public void onButtonClicked(int buttonCode) {}
                });
        ArrayList suggestionsList = new ArrayList(citiesHashMap.keySet());
        Collections.sort(suggestionsList);
        materialSearchBar.setLastSuggestions(suggestionsList);
    }

    private void getWeatherByCityName(String locationKey) {
        compositeDisposable.add(weatherService.getCurrentConditions(
                locationKey,BuildConfig.ACCUWEATHER_API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<CurrentWeather>>()
                        {
                            @Override
                            public void accept(List<CurrentWeather> forecast) {
                                displayData(forecast);  //passes current conditions to be displayed
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                Log.d("MYERROR", "accept: " + throwable.getMessage());
                            }
                        }));
    }
    private void displayData(List<CurrentWeather> sForecast) {
        llblSCity.setText(locationName);
        llblSWeatherText.setText(sForecast.get(0).getWeatherText());
        llblSCurrent.setText(sForecast.get(0).getTemperature().getMetric().getValue() + " °C");

    }
}