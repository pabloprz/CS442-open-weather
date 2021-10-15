package com.iit.pab.openweather.utils;

public enum TempUnit {
    IMPERIAL {
        @Override
        public String getUnit() {
            return "imperial";
        }
    }, METRIC {
        @Override
        public String getUnit() {
            return "metric";
        }
    };

    public abstract String getUnit();
}
