package com.iit.pab.openweather;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iit.pab.openweather.utils.TempUnit;
import com.iit.pab.openweather.weather.Weather;

public class DailyForecastActivity extends AppCompatActivity {

    private Weather weather;
    private TempUnit chosenUnit;
    private DailyAdapter dailyAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        recyclerView = findViewById(R.id.dailyRecyclerView);

        Intent intent = getIntent();

        if (intent.hasExtra(getString(R.string.weather))) {
            weather = (Weather) intent.getSerializableExtra(getString(R.string.weather));
            if (weather != null) {
                setTitle(weather.getLocation().getName());
            }
        }

        if (intent.hasExtra(getString(R.string.chosenUnit))) {
            chosenUnit = (TempUnit) intent.getSerializableExtra(getString(R.string.chosenUnit));
        }

        dailyAdapter = new DailyAdapter(weather.getDailyDetails(), chosenUnit, this);
        recyclerView.setAdapter(dailyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
    }
}
