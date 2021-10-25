package com.iit.pab.openweather.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.iit.pab.openweather.R;

public class SharedPrefUtils {
    private final SharedPreferences prefs;

    private static final String LAT = "lat";
    private static final String LON = "lon";
    private static final String CITY = "city";
    private static final String UNIT = "unit";
    private static final String DEFAULT_LAT = "41.8781136";
    private static final String DEFAULT_LON = "-87.6297982";
    private static final String DEFAULT_CITY = "Chicago, Illinois";

    public SharedPrefUtils(Activity activity) {
        super();

        prefs = activity.getSharedPreferences(activity.getString(R.string.prefsFile),
                Context.MODE_PRIVATE);
    }

    public void saveLocation(LocationDetails details) {
        save(LAT, String.valueOf(details.getLatitude()));
        save(LON, String.valueOf(details.getLongitude()));
        save(CITY, details.getName());
    }

    public void saveChosenUnit(TempUnit unit) {
        save(UNIT, unit.toString());
    }

    public LocationDetails getLocation() {
        return new LocationDetails(getValue(CITY, DEFAULT_CITY), Double.parseDouble(getValue(LAT,
                DEFAULT_LAT)), Double.parseDouble(getValue(LON, DEFAULT_LON)));
    }

    public TempUnit getChosenUnit() {
        return TempUnit.valueOf(getValue(UNIT, TempUnit.IMPERIAL.toString()));
    }

    private void save(String key, String text) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, text);
        editor.apply(); // commit T/F
    }

    private String getValue(String key) {
        return getValue(key, "");
    }

    private String getValue(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }
}
