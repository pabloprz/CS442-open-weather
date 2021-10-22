package com.iit.pab.openweather.utils;

import android.net.Uri;

import com.iit.pab.openweather.MainActivity;
import com.iit.pab.openweather.weather.Weather;
import com.iit.pab.openweather.weather.WeatherDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.net.ssl.HttpsURLConnection;

public class WeatherLoaderRunnable implements Runnable {

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        handleResults(sb.toString());
    }

    private void handleResults(final String json) {
        mainActivity.runOnUiThread(() -> mainActivity.updateData(parseJson(json)));
    }

    private Weather parseJson(String s) {
        Weather weather = null;

        try {
            JSONObject jObjMain = new JSONObject(s);

            weather = new Weather();

            weather.setLatitude(jObjMain.getDouble("lat"));
            weather.setLongitude(jObjMain.getDouble("lon"));
            weather.setTimezone(jObjMain.getString("timezone"));
            weather.setOffset(jObjMain.getInt("timezone_offset"));

            JSONObject current = jObjMain.getJSONObject("current");
            weather.setDateTime(getLocalDateTime(current.getInt("dt"), weather.getOffset()));
            weather.setSunrise(getLocalDateTime(current.getInt("sunrise"), weather.getOffset()));
            weather.setSunset(getLocalDateTime(current.getInt("sunset"), weather.getOffset()));

            weather.setTemperature(current.getDouble("temp"));
            weather.setFeelsLike(current.getDouble("feels_like"));
            weather.setPressure(current.getDouble("pressure"));
            weather.setHumidity(current.getDouble("humidity"));
            weather.setUvIndex(current.getDouble("uvi"));
            weather.setClouds(current.getDouble("clouds"));
            weather.setVisibility(current.getDouble("visibility"));
            weather.setWindSpeed(current.getDouble("wind_speed"));
            weather.setWindDegree(current.getDouble("wind_deg"));

            if (isPresent(current, "wind_gust")) {
                weather.setWindGust(current.getDouble("wind_gust"));
            }

            JSONObject details = (JSONObject) current.getJSONArray("weather").get(0);
            weather.setDetails(new WeatherDetails(details.getInt("id"),
                    details.getString("main"), details.getString("description"),
                    details.getString("icon")));

            if (isPresent(current, "rain")) {
                weather.setRain(details.getJSONObject("rain").getDouble("1h"));
            }

            if (isPresent(current, "snow")) {
                weather.setRain(details.getJSONObject("snow").getDouble("1h"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weather;
    }

    private LocalDateTime getLocalDateTime(long epochSecond, int offset) {
        return LocalDateTime.ofEpochSecond(
                epochSecond + offset, 0, ZoneOffset.UTC);
    }

    private boolean isPresent(JSONObject object, String field) {
        return object.has(field) && !object.isNull(field);
    }
}
