package com.vc19005768.weatherv2;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtil {
    public static final String BASE_URL = "https://dataservice.accuweather.com/forecasts/v1/daily/5day/305605";
    public static final String METRIC_VALUE = "true";
    public static final String METRIC_PARAM = "metric";
    public static final String API_KEY = "353BGmzqTEWNKvBAQkm3DWiOFd3VP0kG";
    public static final String API_PARAM = "apikey";

    public static URL buildURLWeather() {                           //should take the location ID at some point
        Uri urii = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(API_PARAM, API_KEY)
                .appendQueryParameter(METRIC_PARAM, METRIC_VALUE)
                .build();                                           //builds uri

        URL urll = null;
        try {
            urll = new URL(urii.toString());                        //builds url
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i("Our URL:", "buildURLWeather:" + urll);       //Output log message
        return urll;
    }

    public static String getResponse(URL urll) throws IOException {

        HttpsURLConnection httpsURLConn = (HttpsURLConnection) urll.openConnection();

        try {
            InputStream ins = httpsURLConn.getInputStream();
            Scanner scn = new Scanner(ins);
            scn.useDelimiter("//A");

            if (scn.hasNext()) {
                return scn.next();
            } else {
                return null;
            }
        } finally {
            httpsURLConn.disconnect();
        }
    }

}
