package com.springboot.task.WeatherForecastingSystem;

import com.cn.weather.ArrayOfString;
import com.cn.weather.WeatherWS;
import com.cn.weather.WeatherWSSoap;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// 此类用于将城市数据写到本地的json文件中
public class RegionService {
    public static void main(String[] args) {
        RegionService regionService = new RegionService();

        try {
            // 分别获取并保存国内外省份信息
            regionService.getRegionAndSave("中国");
            regionService.getRegionAndSave("外国");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void getRegionAndSave(String region) throws IOException {
        String filePath = null;
        List<Region> regions = new ArrayList<>();

        //创建一个WeatherWS工厂
        WeatherWS factory = new WeatherWS();
        //根据工厂创建一个WeatherWSSoap对象
        WeatherWSSoap weatherWSSoap = factory.getWeatherWSSoap();
        ArrayOfString data = null;
        if("中国".equals(region)){
            data = weatherWSSoap.getRegionProvince();
            filePath = "src/main/java/com/springboot/task/WeatherForecastingSystem/JSON/regionsInDomestic.json";
        }
        else if("外国".equals(region)){
            data = weatherWSSoap.getRegionCountry();
            filePath = "src/main/java/com/springboot/task/WeatherForecastingSystem/JSON/regionsInInternational.json";
        }


        if (data != null) {
            List<String> list = data.getString();
            for (String string : list) {
                List<City> cities = new ArrayList<>();  // 城市集合
                String[] split = string.split(",");
                System.out.println(split[0] + " " + split[1]);

                ArrayOfString supportCityString = weatherWSSoap.getSupportCityString(split[0]);// 查询省份对应的城市
                List<String> string1 = supportCityString.getString();
                for (String s : string1) {
                    String[] split1 = s.split(",");
                    System.out.println(split1[0] + " " + split1[1]);
                    cities.add(new City(split1[0],split1[1]));  // 将城市放在一个集合中
                }
                regions.add(new Region(split[0],split[1],cities));  // 新建省份信息
                System.out.println("------------------------");
            }

            // 保存到json文件
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), regions);
        }
        else System.out.println("data为空");

    }

}
