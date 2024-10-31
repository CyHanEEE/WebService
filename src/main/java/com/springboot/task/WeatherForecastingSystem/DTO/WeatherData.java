package com.springboot.task.WeatherForecastingSystem.DTO;

import java.util.List;

public class WeatherData {
    private String regionInfo; // 省份、地区/洲、国家名
    private String cityName; // 查询的天气预报地区名称
    private String cityId; // 查询的天气预报地区ID
    private String lastUpdate; // 最后更新时间
    private String currentWeatherFacts;  // 当前天气实况
    private String airQualityAndUVIntensity;  // 空气质量、紫外线强度
    private String weatherAndLivingIndex; // 天气和生活指数
    private List<FutureWeather> futureWeather; // 未来天气情况

    // Getters 和 Setters
    public String getRegionInfo() {
        return regionInfo;
    }

    public void setRegionInfo(String regionInfo) {
        this.regionInfo = regionInfo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCurrentWeatherFacts() {
        return currentWeatherFacts;
    }

    public void setCurrentWeatherFacts(String currentWeatherFacts) {
        this.currentWeatherFacts = currentWeatherFacts;
    }

    public String getAirQualityAndUVIntensity() {
        return airQualityAndUVIntensity;
    }

    public void setAirQualityAndUVIntensity(String airQualityAndUVIntensity) {
        this.airQualityAndUVIntensity = airQualityAndUVIntensity;
    }

    public String getWeatherAndLivingIndex() {
        return weatherAndLivingIndex;
    }

    public void setWeatherAndLivingIndex(String weatherAndLivingIndex) {
        this.weatherAndLivingIndex = weatherAndLivingIndex;
    }

    public List<FutureWeather> getFutureWeather() {
        return futureWeather;
    }

    public void setFutureWeather(List<FutureWeather> futureWeather) {
        this.futureWeather = futureWeather;
    }
}