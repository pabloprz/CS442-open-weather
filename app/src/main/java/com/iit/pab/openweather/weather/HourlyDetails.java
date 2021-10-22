package com.iit.pab.openweather.weather;

import java.time.LocalDateTime;

public class HourlyDetails {

    private LocalDateTime dateTime;
    private Double temperature;
    private WeatherDetails details;
    private Double pop;

    public HourlyDetails(LocalDateTime dateTime, Double temperature, WeatherDetails details, Double pop) {
        this.dateTime = dateTime;
        this.temperature = temperature;
        this.details = details;
        this.pop = pop;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public WeatherDetails getDetails() {
        return details;
    }

    public void setDetails(WeatherDetails details) {
        this.details = details;
    }

    public Double getPop() {
        return pop;
    }

    public void setPop(Double pop) {
        this.pop = pop;
    }
}
