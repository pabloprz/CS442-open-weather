package com.iit.pab.openweather;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iit.pab.openweather.utils.DirectionUtils;
import com.iit.pab.openweather.utils.LocationDetails;
import com.iit.pab.openweather.utils.LocationLoaderRunnable;
import com.iit.pab.openweather.utils.TempUnit;
import com.iit.pab.openweather.utils.WeatherLoaderRunnable;
import com.iit.pab.openweather.weather.TemperatureDetails;
import com.iit.pab.openweather.weather.Weather;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private boolean online;
    private TempUnit chosenUnit;
    private LocationDetails location;
    private TextView locationView;
    private TextView dateTimeView;
    private Weather weather;
    private RecyclerView hourlyRecyclerView;
    private HourlyAdapter hourlyAdapter;

    // TODO check all project exceptions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationView = findViewById(R.id.locationView);
        dateTimeView = findViewById(R.id.dateTimeView);

        weather = new Weather();
        hourlyAdapter = new HourlyAdapter(weather.getHourlyDetails(), this);
        hourlyRecyclerView = findViewById(R.id.hourlyView);
        hourlyRecyclerView.setAdapter(hourlyAdapter);
        hourlyRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        // TODO add settings
        this.chosenUnit = TempUnit.IMPERIAL;

        checkNetworkConnection();

        if (online) {
            new Thread(new LocationLoaderRunnable("Chicago, IL", this)).start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        changeIcon(menu.findItem(R.id.units_toggle));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (online) {
            if (item.getItemId() == R.id.units_toggle) {
                // Toggle default unit
                this.chosenUnit = this.chosenUnit.equals(TempUnit.IMPERIAL) ? TempUnit.METRIC :
                        TempUnit.IMPERIAL;
                changeIcon(item);
            } else if (item.getItemId() == R.id.daily_show) {
                // Move to daily activity
                Intent intent = new Intent(this, DailyForecastActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.location_change) {
                // TODO Change location
            }
        } else {
            Toast.makeText(this, R.string.not_available_offline, Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateData(Weather weather) {
        this.weather = weather;

        if (weather == null) {
            Toast.makeText(this, "Please Enter a Valid City Name", Toast.LENGTH_SHORT).show();
            return;
        }

        TextView dateTime = findViewById(R.id.dateTimeView);
        dateTime.setText(formatDateTime(weather.getDateTime()));

        TextView temp = findViewById(R.id.tempView);
        temp.setText(tempToText(weather.getTemperature()));

        TextView feelsLike = findViewById(R.id.feelsLikeView);
        feelsLike.setText(String.format(Locale.getDefault(), "Feels Like %.0fº" + getTempUnit(),
                weather.getFeelsLike()));

        TextView description = findViewById(R.id.weatherDescriptionView);
        description.setText(String.format(Locale.getDefault(), "%s%s",
                weather.getDetails().getDescription().substring(0, 1).toUpperCase(),
                weather.getDetails().getDescription().substring(1)));

        TextView winds = findViewById(R.id.windsView);
        winds.setText(String.format(Locale.getDefault(), "Winds: %S at %.0f %s",
                DirectionUtils.getDirection(weather.getWindDegree()), weather.getWindSpeed(),
                getSpeedUnit()));

        TextView humidity = findViewById(R.id.humidityView);
        humidity.setText(String.format(Locale.getDefault(), "Humidity: %.0f%%",
                weather.getHumidity()));

        TextView uvIndex = findViewById(R.id.uvIndexView);
        uvIndex.setText(String.format(Locale.getDefault(), "UV Index: %.0f", weather.getUvIndex()));

        TextView visibility = findViewById(R.id.visibilityView);
        visibility.setText(String.format(Locale.getDefault(), "Visibility: %.1f",
                weather.getVisibility()));

        TextView sunrise = findViewById(R.id.sunrise);
        sunrise.setText(String.format(Locale.getDefault(), "Sunrise: %s",
                formatTime(weather.getSunrise())));

        TextView sunset = findViewById(R.id.sunset);
        sunset.setText(String.format(Locale.getDefault(), "Sunset: %s",
                formatTime(weather.getSunset())));

        TemperatureDetails tempDetails = weather.getDailyDetails().get(0).getTemperature();
        TextView morn = findViewById(R.id.morningView);
        morn.setText(tempToText(tempDetails.getMorning()));
        TextView day = findViewById(R.id.dayView);
        day.setText(tempToText(tempDetails.getDay()));
        TextView eve = findViewById(R.id.eveningView);
        eve.setText(tempToText(tempDetails.getEvening()));
        TextView night = findViewById(R.id.nightView);
        night.setText(tempToText(tempDetails.getNight()));

        hourlyAdapter.notifyDataSetChanged();
    }

    public String tempToText(double temp) {
        return String.format(Locale.getDefault(), "%.0fº" + getTempUnit(), temp);
    }

    private void changeIcon(MenuItem menuItem) {
        menuItem.setIcon(ContextCompat.getDrawable(this,
                this.chosenUnit.equals(TempUnit.IMPERIAL) ? R.drawable.units_f :
                        R.drawable.units_c));
    }

    private void checkNetworkConnection() {
        online = hasNetworkConnection();
        if (!online) {
            dateTimeView.setText(R.string.no_connection);
        }
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public void locationDetailsArrived(LocationDetails locationDetails) {
        location = locationDetails;
        if (location != null) {
            locationView.setText(location.getName());
            WeatherLoaderRunnable runnable = new WeatherLoaderRunnable(this, location, chosenUnit
                    , weather);
            new Thread(runnable).start();
        } else {
            Toast.makeText(this, "Failed to load location info", Toast.LENGTH_SHORT).show();
        }
    }

    private String formatDateTime(LocalDateTime ldt) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MMM dd h:mm a, yyyy",
                Locale.getDefault());
        return ldt.format(dtf);
    }

    public String formatTime(LocalDateTime ldt) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault());
        return ldt.format(dtf);
    }

    private String getTempUnit() {
        return chosenUnit == TempUnit.IMPERIAL ? "F" : "C";
    }

    private String getSpeedUnit() {
        return chosenUnit == TempUnit.IMPERIAL ? "mph" : "kmh";
    }
}
