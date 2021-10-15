package com.iit.pab.openweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private TempUnit chosenUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        return super.onOptionsItemSelected(item);
    }

    private void changeIcon(MenuItem menuItem) {
        menuItem.setIcon(ContextCompat.getDrawable(this,
                this.chosenUnit.equals(TempUnit.IMPERIAL) ? R.drawable.units_f : R.drawable.units_c));
    }
}
