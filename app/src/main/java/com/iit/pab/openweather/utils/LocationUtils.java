package com.iit.pab.openweather.utils;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

public class LocationUtils {

    public static LocationDetails getLocationName(String userProvidedLocation, Activity context) {
        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> address =
                    geocoder.getFromLocationName(userProvidedLocation, 1);
            if (address == null || address.isEmpty()) {
                // Nothing returned!
                return null;
            }
            String country = address.get(0).getCountryCode();
            String p1 = "";
            String p2 = "";
            if (country.equals("US")) {
                p1 = address.get(0).getLocality();
                p2 = address.get(0).getAdminArea();
            } else {
                p1 = address.get(0).getLocality();
                if (p1 == null)
                    p1 = address.get(0).getSubAdminArea();
                p2 = address.get(0).getCountryName();
            }
            return new LocationDetails(p1 + ", " + p2, address.get(0).getLatitude(), address.get(0).getLongitude());
        } catch (IOException e) {
            // Failure to get an Address object
            return null;
        }
    }
}
