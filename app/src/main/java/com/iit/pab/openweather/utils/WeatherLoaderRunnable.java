package com.iit.pab.openweather.utils;

import android.net.Uri;
import android.util.Log;

import com.iit.pab.openweather.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherLoaderRunnable implements Runnable {

    private static final String TAG = "WeatherLoaderRunnable";
    private final MainActivity mainActivity;
    private final LocationDetails location;
    private final TempUnit tempUnit;
    private static final String DATA_URL = "https://api.openweathermap.org/data/2.5/onecall";
    private static final String API_KEY = "b9bd7fe955df909c89670c86bea812f9";

    public WeatherLoaderRunnable(MainActivity mainActivity, LocationDetails location, TempUnit tempUnit) {
        this.mainActivity = mainActivity;
        this.location = location;
        this.tempUnit = tempUnit;
    }


    @Override
    public void run() {
        Uri.Builder builder = Uri.parse(DATA_URL).buildUpon();

        builder.appendQueryParameter("lat", String.valueOf(location.getLatitude()));
        builder.appendQueryParameter("lon", String.valueOf(location.getLongitude()));
        builder.appendQueryParameter("appid", API_KEY);
        builder.appendQueryParameter("units", tempUnit.getUnit());
        builder.appendQueryParameter("lang", "EN");
        builder.appendQueryParameter("exclude", "minutely");

        String urlToUse = builder.build().toString();
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();

            if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                // Something went wrong
            }

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            Log.d(TAG, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
