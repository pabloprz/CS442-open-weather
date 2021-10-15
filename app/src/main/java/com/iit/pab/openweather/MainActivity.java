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

import com.iit.pab.openweather.utils.LocationDetails;
import com.iit.pab.openweather.utils.LocationUtils;

public class MainActivity extends AppCompatActivity {

    private boolean online;
    private TempUnit chosenUnit;
    private LocationDetails location;
    private TextView locationView;
    private TextView dateTimeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationView = findViewById(R.id.locationView);
        dateTimeView = findViewById(R.id.dateTimeView);

        checkNetworkConnection();

        if (online) {
            location = LocationUtils.getLocationName("Chicago", this);
            if (location != null) {
                locationView.setText(location.getName());
            }

        }

        // TODO add settings
        this.chosenUnit = TempUnit.IMPERIAL;
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
                this.chosenUnit = this.chosenUnit.equals(TempUnit.IMPERIAL) ? TempUnit.METRIC : TempUnit.IMPERIAL;
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

    private void changeIcon(MenuItem menuItem) {
        menuItem.setIcon(ContextCompat.getDrawable(this,
                this.chosenUnit.equals(TempUnit.IMPERIAL) ? R.drawable.units_f : R.drawable.units_c));
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
}
