package com.iit.pab.openweather;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.iit.pab.openweather.utils.DirectionUtils;
import com.iit.pab.openweather.utils.FormattingUtils;
import com.iit.pab.openweather.utils.HourlyElementOnClickListener;
import com.iit.pab.openweather.utils.LocationDetails;
import com.iit.pab.openweather.utils.LocationLoaderRunnable;
import com.iit.pab.openweather.utils.SharedPrefUtils;
import com.iit.pab.openweather.utils.TempUnit;
import com.iit.pab.openweather.utils.WeatherLoaderRunnable;
import com.iit.pab.openweather.weather.TemperatureDetails;
import com.iit.pab.openweather.weather.Weather;

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
    private SwipeRefreshLayout swiper;
    private SharedPrefUtils sharedPrefs;

    // TODO check all project exceptions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationView = findViewById(R.id.locationView);
        dateTimeView = findViewById(R.id.dateTimeView);
        swiper = findViewById(R.id.swiperefresh);

        sharedPrefs = new SharedPrefUtils(this);
        chosenUnit = sharedPrefs.getChosenUnit();
        location = sharedPrefs.getLocation();

        weather = new Weather();
        hourlyRecyclerView = findViewById(R.id.hourlyView);
        hourlyAdapter = new HourlyAdapter(weather.getHourlyDetails(), this,
                new HourlyElementOnClickListener(this, hourlyRecyclerView,
                        weather.getHourlyDetails()), chosenUnit);
        hourlyRecyclerView.setAdapter(hourlyAdapter);
        hourlyRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        swiper.setOnRefreshListener(this::reload);

        refreshLocationView(location.getName());
        reload();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        changeUnitsIcon(menu.findItem(R.id.units_toggle));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (online) {
            if (item.getItemId() == R.id.units_toggle) {
                // Toggle default unit
                chosenUnit = chosenUnit.equals(TempUnit.IMPERIAL) ? TempUnit.METRIC :
                        TempUnit.IMPERIAL;
                changeUnitsIcon(item);
                reload();
                sharedPrefs.saveChosenUnit(chosenUnit);
            } else if (item.getItemId() == R.id.daily_show) {
                // Move to daily activity
                Intent intent = new Intent(this, DailyForecastActivity.class);
                intent.putExtra(getString(R.string.weather), weather);
                intent.putExtra(getString(R.string.chosenUnit), chosenUnit);
                startActivity(intent);
            } else if (item.getItemId() == R.id.location_change) {
                openLocationDialog();
            }
        } else {
            Toast.makeText(this, R.string.not_available_offline, Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void reload() {
        if (checkNetworkConnection() && location != null) {
            this.weather.getHourlyDetails().clear();
            this.weather.getDailyDetails().clear();
            WeatherLoaderRunnable runnable = new WeatherLoaderRunnable(this, location, chosenUnit
                    , weather);
            new Thread(runnable).start();
        }
    }

    private void openLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText et = new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        et.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setView(et);

        builder.setPositiveButton(R.string.ok,
                (d, id) -> this.locationChanged(et.getText().toString()));
        builder.setNegativeButton(R.string.cancel, (d, id) -> {
        });

        builder.setTitle("Enter a Location");
        builder.setMessage("For US locations, enter as 'City' or 'City, State' \n\nFor " +
                "international locations enter as 'City, Country'");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updateData(Weather weather) {
        this.weather = weather;

        if (weather == null) {
            Toast.makeText(this, "Please Enter a Valid City Name", Toast.LENGTH_SHORT).show();
            return;
        }

        TextView dateTime = findViewById(R.id.dateTimeView);
        dateTime.setText(FormattingUtils.formatDateTime(weather.getDateTime()));

        TextView temp = findViewById(R.id.tempView);
        temp.setText(FormattingUtils.tempToText(weather.getTemperature(), chosenUnit));

        ImageView currentIcon = findViewById(R.id.currentWeatherImage);
        currentIcon.setImageResource(getResources().getIdentifier("_" + weather.getDetails().getIcon(), "drawable", getPackageName()));

        TextView feelsLike = findViewById(R.id.feelsLikeView);
        feelsLike.setText(String.format(Locale.getDefault(),
                "Feels Like %.0fº" + chosenUnit.getSymbol(), weather.getFeelsLike()));

        TextView description = findViewById(R.id.weatherDescriptionView);
        description.setText(String.format(Locale.getDefault(), "%s%s",
                weather.getDetails().getDescription().substring(0, 1).toUpperCase(),
                weather.getDetails().getDescription().substring(1)));

        TextView winds = findViewById(R.id.windsView);
        winds.setText(String.format(Locale.getDefault(), "Winds: %S at %.0f %s", DirectionUtils.getDirection(weather.getWindDegree()), weather.getWindSpeed(), getSpeedUnit()));

        TextView humidity = findViewById(R.id.humidityView);
        humidity.setText(String.format(Locale.getDefault(), "Humidity: %.0f%%",
                weather.getHumidity()));

        TextView uvIndex = findViewById(R.id.uvIndexView);
        uvIndex.setText(String.format(Locale.getDefault(), "UV Index: %.0f", weather.getUvIndex()));

        // Visibility is converted to miles (if chosen unit is imperial) or km (if metric)
        // bc visibility data comes in metres
        TextView visibility = findViewById(R.id.visibilityView);
        visibility.setText(String.format(Locale.getDefault(), "Visibility: %.1f %s",
                getVisibilityInCurrentUnit(weather.getVisibility()), getDistanceUnit()));

        TextView sunrise = findViewById(R.id.sunrise);
        sunrise.setText(String.format(Locale.getDefault(), "Sunrise: %s",
                FormattingUtils.formatTime(weather.getSunrise())));

        TextView sunset = findViewById(R.id.sunset);
        sunset.setText(String.format(Locale.getDefault(), "Sunset: %s",
                FormattingUtils.formatTime(weather.getSunset())));

        TemperatureDetails tempDetails = weather.getDailyDetails().get(0).getTemperature();
        TextView morn = findViewById(R.id.morningView);
        morn.setText(FormattingUtils.tempToText(tempDetails.getMorning(), chosenUnit));
        TextView day = findViewById(R.id.dayView);
        day.setText(FormattingUtils.tempToText(tempDetails.getDay(), chosenUnit));
        TextView eve = findViewById(R.id.eveningView);
        eve.setText(FormattingUtils.tempToText(tempDetails.getEvening(), chosenUnit));
        TextView night = findViewById(R.id.nightView);
        night.setText(FormattingUtils.tempToText(tempDetails.getNight(), chosenUnit));
        hourlyAdapter.notifyDataSetChanged();

        swiper.setRefreshing(false);
    }

    private void changeUnitsIcon(MenuItem menuItem) {
        menuItem.setIcon(ContextCompat.getDrawable(this,
                this.chosenUnit.equals(TempUnit.IMPERIAL) ? R.drawable.units_f :
                        R.drawable.units_c));
    }

    private boolean checkNetworkConnection() {
        online = hasNetworkConnection();
        if (!online) {
            dateTimeView.setText(R.string.no_connection);
        }

        return online;
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private void locationChanged(String newLocation) {
        if (checkNetworkConnection()) {
            new Thread(new LocationLoaderRunnable(newLocation, this)).start();
        }
    }

    public void locationDetailsArrived(LocationDetails locationDetails) {
        location = locationDetails;
        if (location != null) {
            refreshLocationView(location.getName());
            reload();
            sharedPrefs.saveLocation(location);
        } else {
            Toast.makeText(this, "Failed to load location info", Toast.LENGTH_SHORT).show();
        }
    }

    private String getSpeedUnit() {
        return chosenUnit == TempUnit.IMPERIAL ? "mph" : "kmh";
    }

    private String getDistanceUnit() {
        return chosenUnit == TempUnit.IMPERIAL ? "mi" : "m";
    }

    public TempUnit getChosenUnit() {
        return chosenUnit;
    }

    private void refreshLocationView(String locationName) {
        locationView.setText(locationName);
    }

    private double getVisibilityInCurrentUnit(double metresVisibility) {
        // If metric, transform meters to km. If imperial, transform meters to miles
        return chosenUnit == TempUnit.METRIC ? metresVisibility / 1000 :
                metresVisibility * 0.00062137;
    }
}
