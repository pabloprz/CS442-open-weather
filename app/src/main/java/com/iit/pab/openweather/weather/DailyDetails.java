package com.iit.pab.openweather.weather;

import java.time.LocalDateTime;

public class DailyDetails {

    private LocalDateTime dateTime;
    private TemperatureDetails temperature;
    private WeatherDetails weatherDetail;
    private Double pop;
    private Double uvIndex;

    public DailyDetails(LocalDateTime dateTime, TemperatureDetails temperature, WeatherDetails weatherDetail, Double pop, Double uvIndex) {
        this.dateTime = dateTime;
        this.temperature = temperature;
        this.weatherDetail = weatherDetail;
        this.pop = pop;
        this.uvIndex = uvIndex;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public TemperatureDetails getTemperature() {
        return temperature;
    }

    public void setTemperature(TemperatureDetails temperature) {
        this.temperature = temperature;
    }

    public WeatherDetails getWeatherDetail() {
        return weatherDetail;
    }

    public void setWeatherDetail(WeatherDetails weatherDetail) {
        this.weatherDetail = weatherDetail;
    }

    public Double getPop() {
        return pop;
    }

    public void setPop(Double pop) {
        this.pop = pop;
    }

    public Double getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(Double uvIndex) {
        this.uvIndex = uvIndex;
    }
}
