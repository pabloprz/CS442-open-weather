package com.iit.pab.openweather.utils;

import com.iit.pab.openweather.MainActivity;

public class LocationLoaderRunnable implements Runnable {

    private final String userProvidedLocation;
    private final MainActivity mainActivity;

    public LocationLoaderRunnable(String userProvidedLocation, MainActivity mainActivity) {
        this.userProvidedLocation = userProvidedLocation;
        this.mainActivity = mainActivity;
    }

    @Override
    public void run() {
        LocationDetails locationDetails = LocationUtils.getLocationDetails(userProvidedLocation, mainActivity);
        mainActivity.runOnUiThread(() -> mainActivity.locationDetailsArrived(locationDetails));
    }
}
