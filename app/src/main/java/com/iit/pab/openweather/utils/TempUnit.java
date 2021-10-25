package com.iit.pab.openweather.utils;

import java.io.Serializable;

public enum TempUnit implements Serializable {
    IMPERIAL {
        @Override
        public String getUnit() {
            return "imperial";
        }

        @Override
        public String getSymbol() {
            return "F";
        }
    }, METRIC {
        @Override
        public String getUnit() {
            return "metric";
        }

        @Override
        public String getSymbol() {
            return "C";
        }
    };

    public abstract String getUnit();

    public abstract String getSymbol();
}
