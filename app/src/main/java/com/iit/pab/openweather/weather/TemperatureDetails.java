package com.iit.pab.openweather.weather;

import java.io.Serializable;

public class TemperatureDetails implements Serializable {

    private Double day;
    private Double night;
    private Double evening;
    private Double morning;
    private Double minimum;
    private Double maximum;

    public TemperatureDetails(Double day, Double night, Double evening, Double morning,
                              Double minimum, Double maximum) {
        this.day = day;
        this.night = night;
        this.evening = evening;
        this.morning = morning;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public Double getDay() {
        return day;
    }

    public void setDay(Double day) {
        this.day = day;
    }

    public Double getNight() {
        return night;
    }

    public void setNight(Double night) {
        this.night = night;
    }

    public Double getEvening() {
        return evening;
    }

    public void setEvening(Double evening) {
        this.evening = evening;
    }

    public Double getMorning() {
        return morning;
    }

    public void setMorning(Double morning) {
        this.morning = morning;
    }

    public Double getMinimum() {
        return minimum;
    }

    public void setMinimum(Double minimum) {
        this.minimum = minimum;
    }

    public Double getMaximum() {
        return maximum;
    }

    public void setMaximum(Double maximum) {
        this.maximum = maximum;
    }
}
