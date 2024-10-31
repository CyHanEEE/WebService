package com.springboot.task.WeatherForecastingSystem;

import java.util.List;

// 该类为数据结构类，分别为省份和城市数据，省份下对应不同的城市
class Region {
    private String regionName;
    private String regionID;
    private List<City> cities;

    // 构造方法
    public Region(){}
    public Region(String regionName, String regionID, List<City> cities) {
        this.regionName = regionName;
        this.regionID = regionID;
        this.cities = cities;
    }

    // Getters and Setters
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionID() {
        return regionID;
    }

    public void setRegionID(String regionID) {
        this.regionID = regionID;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}

class City {
    private String cityName;
    private String cityID;

    // 构造方法
    public City(){}
    public City(String cityName, String cityID) {
        this.cityName = cityName;
        this.cityID = cityID;
    }

    // Getters and Setters
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }
}
