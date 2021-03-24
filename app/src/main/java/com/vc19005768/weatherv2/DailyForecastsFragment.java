package com.vc19005768.weatherv2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vc19005768.weatherv2.fiveDayForecast.FiveDayForecast;
import com.vc19005768.weatherv2.retrofit.IAccuWeather;
import com.vc19005768.weatherv2.retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class DailyForecastsFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_LOCATION_KEY = "locationKey";
    private String locationKey;


    private int mColumnCount = 1;
    private RecyclerView weatherDataList;

    private IAccuWeather weatherService;
    private CompositeDisposable compositeDisposable;


    public DailyForecastsFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getRetrofit();
        weatherService = retrofit.create(IAccuWeather.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }


    public static DailyForecastsFragment newInstance(int columnCount, String lkey) {
        DailyForecastsFragment fragment = new DailyForecastsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LOCATION_KEY, lkey);
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            locationKey = getArguments().getString(ARG_LOCATION_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            weatherDataList = recyclerView; //sets global recycler view

            // Make the call using Retrofit and RxJava
            compositeDisposable.add(weatherService.getFiveDayForecast
                    (locationKey,BuildConfig.ACCUWEATHER_API_KEY,true)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<FiveDayForecast>()
                    {
                        @Override
                        public void accept(FiveDayForecast forecast) {
                            displayData(forecast);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) {
                            Log.d("MYERROR", "accept: " + throwable.getMessage());
                        }
                    }));

            //URL url = NetworkUtil.buildURLWeather();
            //new FetchWeatherData().execute(url); //runs async method
        }
        return view;
    }

    private void displayData(FiveDayForecast fiveDayForecast) {
        weatherDataList.setAdapter(new DailyForecastsRecyclerViewAdapter(
                fiveDayForecast.getDailyForecasts()));
    }





    //Native Asynchronous API Fetch using NetworkUtil Class
//    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
//    class FetchWeatherData extends AsyncTask<URL,Void,String> {
//
//        @Override
//        protected String doInBackground(URL... urls) {
//
//            URL weatherURL = urls[0]; //potentially put loop here for multiple urls
//            String weatherResult = null;
//            try {
//                weatherResult = NetworkUtil.getResponse(weatherURL);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Log.i("OurData", weatherResult);
//            return weatherResult;
//        }
//        @Override
//        protected void onPostExecute(String weatherJSON) {
//            if (weatherJSON != null) {
//                Gson gson = new Gson();
//                FiveDayForecast weatherData = gson.fromJson(weatherJSON, FiveDayForecast.class);
//                weatherDataList.setAdapter(new DailyForecastsRecyclerViewAdapter(
//                        weatherData.getDailyForecasts()));
//            }
//        }
//    }

}