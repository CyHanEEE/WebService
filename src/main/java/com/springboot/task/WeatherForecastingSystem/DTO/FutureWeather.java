package com.springboot.task.WeatherForecastingSystem.DTO;

public class FutureWeather {
    private String description;      // 天气概况
    private String temperature;       // 气温
    private String wind;             // 风力/风向
    private String icon1;            // 天气图标1
    private String icon2;            // 天气图标2

    // Constructor

    public FutureWeather() {
    }

    public FutureWeather(String description, String temperature, String wind, String icon1, String icon2) {
        this.description = description;
        this.temperature = temperature;
        this.wind = wind;
        this.icon1 = icon1;
        this.icon2 = icon2;
    }

    // Getters and Setters

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getIcon1() {
        return icon1;
    }

    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }

    public String getIcon2() {
        return icon2;
    }

    public void setIcon2(String icon2) {
        this.icon2 = icon2;
    }
}