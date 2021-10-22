package com.iit.pab.openweather.weather;

import java.time.LocalDateTime;
import java.util.List;

public class Weather {

    private double latitude;
    private double longitude;

    private String timezone;
    private int offset;

    private LocalDateTime dateTime;
    private LocalDateTime sunrise;
    private LocalDateTime sunset;

    private Double temperature;
    private Double feelsLike;
    private Double pressure;
    private Double humidity;

    private Double uvIndex;
    private Double clouds;
    private Double visibility;
    private Double windSpeed;
    private Double windDegree;
    private Double windGust;

    private WeatherDetails details;

    private Double rain;
    private Double snow;

    private List<HourlyDetails> hourlyDetails;
    private List<DailyDetails> dailyDetails;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getSunrise() {
        return sunrise;
    }

    public void setSunrise(LocalDateTime sunrise) {
        this.sunrise = sunrise;
    }

    public LocalDateTime getSunset() {
        return sunset;
    }

    public void setSunset(LocalDateTime sunset) {
        this.sunset = sunset;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(Double uvIndex) {
        this.uvIndex = uvIndex;
    }

    public Double getClouds() {
        return clouds;
    }

    public void setClouds(Double clouds) {
        this.clouds = clouds;
    }

    public Double getVisibility() {
        return visibility;
    }

    public void setVisibility(Double visibility) {
        this.visibility = visibility;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(Double windDegree) {
        this.windDegree = windDegree;
    }

    public Double getWindGust() {
        return windGust;
    }

    public void setWindGust(Double windGust) {
        this.windGust = windGust;
    }

    public WeatherDetails getDetails() {
        return details;
    }

    public void setDetails(WeatherDetails details) {
        this.details = details;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    public Double getSnow() {
        return snow;
    }

    public void setSnow(Double snow) {
        this.snow = snow;
    }

    public List<HourlyDetails> getHourlyDetails() {
        return hourlyDetails;
    }

    public void setHourlyDetails(List<HourlyDetails> hourlyDetails) {
        this.hourlyDetails = hourlyDetails;
    }

    public List<DailyDetails> getDailyDetails() {
        return dailyDetails;
    }

    public void setDailyDetails(List<DailyDetails> dailyDetails) {
        this.dailyDetails = dailyDetails;
    }
}
