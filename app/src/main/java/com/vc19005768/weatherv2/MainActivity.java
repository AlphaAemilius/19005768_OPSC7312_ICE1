package com.vc19005768.weatherv2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.vc19005768.weatherv2.location.AccuWeatherLocation;
import com.vc19005768.weatherv2.retrofit.IAccuWeather;
import com.vc19005768.weatherv2.retrofit.RetrofitClient;
import com.vc19005768.weatherv2.ui.main.SectionsPagerAdapter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private IAccuWeather weatherService;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//        ViewPager viewPager = findViewById(R.id.view_pager);
//        viewPager.setAdapter(sectionsPagerAdapter);
//        TabLayout tabs = findViewById(R.id.tabs);
//        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View rootview = getWindow().getDecorView().getRootView();
                Bitmap currentScreenshot =  ProcessImageUtil.takeScreenshot(rootview);  // calls take screenshot from ProcessImageUtil
                ProcessImageUtil.storeScreenshot(MainActivity.this, currentScreenshot,"Weather Today");// stores screenshot (ProcessImageUtil)
                ProcessImageUtil.pushToInstagram(MainActivity.this,"/Weather Today"); // opens the sharesheet
            }
        });


        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getRetrofit();
        weatherService = retrofit.create(IAccuWeather.class);

        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this);
        checkPermissionsAndRequestLocation();

    }

    private void checkPermissionsAndRequestLocation() {
        int hasFineLocationPermission = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCourseLocationPermission = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED &&
                hasCourseLocationPermission != PackageManager.PERMISSION_GRANTED) {
            final String[] permissions = {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION};
            // Request permission - this is asynchronous
            ActivityCompat.requestPermissions(this, permissions, 0);
        } else {
            // We have permission, so now ask for the location
            getLocationAndCreateUI();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocationAndCreateUI() {
        LocationRequest locationRequest = buildLocationRequest();
        LocationCallback locationCallback = buildLocationCallBack();
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                locationCallback, Looper.myLooper());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
        // is this for our request?
        if (requestCode == 0) {
            if (grantResults.length > 0 &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                            grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                getLocationAndCreateUI();
            } else {
                Toast.makeText(MainActivity.this,
                        "Location permission denied",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    private LocationRequest buildLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10.0f);
        return locationRequest;
    }

    private LocationCallback buildLocationCallBack() {
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                Log.i("LocationResult", "onLocationResult: " + location);


                compositeDisposable.add(weatherService.getLocationByPosition(
                        location.getLatitude() + "," +
                                location.getLongitude(),
                        BuildConfig.ACCUWEATHER_API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<AccuWeatherLocation>() {
                            @Override
                            public void accept(AccuWeatherLocation location) // this is the freshly deserialized root class
                                    throws Exception {
                                displayData(location);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d("MYERROR", "accept: " + throwable.getMessage());
                            }
                        }));

//                SectionsPagerAdapter sectionsPagerAdapter =
//                        new SectionsPagerAdapter(
//                                MainActivity.this,
//                                getSupportFragmentManager());
//                ViewPager viewPager = findViewById(R.id.view_pager);
//                viewPager.setAdapter(sectionsPagerAdapter);
//                TabLayout tabs = findViewById(R.id.tabs);
//                tabs.setupWithViewPager(viewPager);
            }
        };
        return locationCallback;
    }

    private void displayData(AccuWeatherLocation location) { //location data injected into the pageadapter
        SectionsPagerAdapter sectionsPagerAdapter =  new SectionsPagerAdapter(
                 MainActivity.this,
                        getSupportFragmentManager(),
                        location.getLocalizedName(),
                        location.getKey());


        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }


}