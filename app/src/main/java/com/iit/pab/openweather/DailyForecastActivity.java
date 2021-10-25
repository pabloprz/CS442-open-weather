package com.iit.pab.openweather;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.iit.pab.openweather.weather.Weather;

public class DailyForecastActivity extends AppCompatActivity {

    private Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.weather))) {
            weather = (Weather) intent.getSerializableExtra(getString(R.string.weather));
            if (weather != null) {
                setTitle(weather.getLocation().getName());
            }
        }
    }
}
