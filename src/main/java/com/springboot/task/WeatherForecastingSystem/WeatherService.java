package com.springboot.task.WeatherForecastingSystem;

import com.cn.weather.ArrayOfString;
import com.cn.weather.WeatherWS;
import com.cn.weather.WeatherWSSoap;
import com.springboot.task.WeatherForecastingSystem.DTO.FutureWeather;
import com.springboot.task.WeatherForecastingSystem.DTO.WeatherData;

import java.util.ArrayList;
import java.util.List;


public class WeatherService {
    public static WeatherData fetchWeather(String cityName) {
        // 创建一个WeatherWS工厂
        WeatherWS factory = new WeatherWS();
        // 根据工厂创建一个WeatherWSSoap对象
        WeatherWSSoap weatherWSSoap = factory.getWeatherWSSoap();

        // 获取天气信息
        ArrayOfString weatherInfo = weatherWSSoap.getWeather(cityName, "");
        List<String> infoList = weatherInfo.getString(); // 获取 List<String>

        // 解析返回的数据
        if (infoList != null && !infoList.isEmpty()) {
            WeatherData weatherData = new WeatherData();

            // 假设按照文档格式解析数据
            weatherData.setRegionInfo(infoList.get(0)); // 省份
            weatherData.setCityName(infoList.get(1)); // 城市名称
            weatherData.setCityId(infoList.get(2)); // 城市ID
            weatherData.setLastUpdate(infoList.get(3)); // 最后更新时间
            weatherData.setCurrentWeatherFacts(infoList.get(4));  // 当前天气实况
            weatherData.setAirQualityAndUVIntensity(infoList.get(5));  // 空气质量、紫外线强度
            weatherData.setWeatherAndLivingIndex(infoList.get(6)); // 天气和生活指数

            // 解析未来天气数据
            List<FutureWeather> futureWeatherList = new ArrayList<>();

            int daysCount = (infoList.size() - 7) / 5; // 每天的天气数据占用5个位置
            for (int i = 0; i < daysCount; i++) {
                String date = infoList.get(7 + i * 5);  // 每天概况的索引
                String temperature = infoList.get(8 + i * 5); // 每天气温的索引
                String wind = infoList.get(9 + i * 5); // 每天风力/风向的索引
                String icon1 = infoList.get(10 + i * 5); // 每天天气图标1的索引
                String icon2 = infoList.get(11 + i * 5); // 每天天气图标2的索引

                FutureWeather futureWeather = new FutureWeather(date, temperature, wind, icon1, icon2);
                futureWeatherList.add(futureWeather);
            }
            weatherData.setFutureWeather(futureWeatherList);


            return weatherData;
        }

        return null; // 如果没有数据返回null
    }
}