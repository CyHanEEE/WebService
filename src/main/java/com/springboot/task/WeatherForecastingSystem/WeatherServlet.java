package com.springboot.task.WeatherForecastingSystem;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.task.WeatherForecastingSystem.DTO.CityDTO;
import com.springboot.task.WeatherForecastingSystem.DTO.ProvinceDTO;
import com.springboot.task.WeatherForecastingSystem.DTO.WeatherData;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "WeatherServlet", urlPatterns = {"/api/provinces", "/api/cities", "/api/weather", "/api/internationalProvinces"})
public class WeatherServlet extends HttpServlet {
    // 使用相对路径会出现无法找到的情况，因此这里只能使用绝对路径
    private static final String DOMESTIC_PROVINCE_FILE_PATH = "D:\\111\\1各种各样杂七杂八的实验\\软件体系结构\\实验四\\Task1\\src\\main\\java\\com\\springboot\\task\\WeatherForecastingSystem\\JSON\\regionsInDomestic.json"; // 国内省份和城市信息的文件路径
    private static final String INTERNATIONAL_PROVINCE_FILE_PATH = "D:\\111\\1各种各样杂七杂八的实验\\软件体系结构\\实验四\\Task1\\src\\main\\java\\com\\springboot\\task\\WeatherForecastingSystem\\JSON\\regionsInInternational.json"; // 国外省份和城市信息的文件路径
    // 读取 JSON 文件数据
    private List<Region> loadRegionsFromFile(String region) throws IOException {
        // 7540
        ObjectMapper mapper = new ObjectMapper();
        List<Region> regions = new ArrayList<>();

        if("domestic".equals(region)) {
            regions.addAll(Arrays.asList(mapper.readValue(new File(DOMESTIC_PROVINCE_FILE_PATH), Region[].class)));
        } else if("international".equals(region)) {
            regions.addAll(Arrays.asList(mapper.readValue(new File(INTERNATIONAL_PROVINCE_FILE_PATH), Region[].class)));
        }
        // 合并两个条件的结果
        if ("all".equals(region)) {
            List<Region> domesticRegions = Arrays.asList(mapper.readValue(new File(DOMESTIC_PROVINCE_FILE_PATH), Region[].class));
            List<Region> internationalRegions = Arrays.asList(mapper.readValue(new File(INTERNATIONAL_PROVINCE_FILE_PATH), Region[].class));
            regions.addAll(domesticRegions);
            regions.addAll(internationalRegions);
        }

        return regions;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();
        resp.setContentType("application/json;charset=UTF-8");

        if ("/api/provinces".equals(path)) {
            // 返回省份列表
            List<Region> regions = loadRegionsFromFile("domestic");
            List<ProvinceDTO> provinces = regions.stream()
                    .map(region -> new ProvinceDTO(region.getRegionID(), region.getRegionName()))
                    .collect(Collectors.toList());
            new ObjectMapper().writeValue(resp.getWriter(), provinces);

        } else if("/api/internationalProvinces".equals(path)) {
            // 返回省份列表
            List<Region> regions = loadRegionsFromFile("international");
            List<ProvinceDTO> provinces = regions.stream()
                    .map(region -> new ProvinceDTO(region.getRegionID(), region.getRegionName()))
                    .collect(Collectors.toList());
            new ObjectMapper().writeValue(resp.getWriter(), provinces);

        } else if ("/api/cities".equals(path)) {
            // 根据省份 ID 返回对应城市列表
            String provinceId = req.getParameter("provinceId");
            List<Region> regions = loadRegionsFromFile("all");
            List<CityDTO> cities = regions.stream()
                    .filter(region -> region.getRegionID().equals(provinceId))
                    .flatMap(region -> region.getCities().stream())
                    .map(city -> new CityDTO(city.getCityID(), city.getCityName()))
                    .collect(Collectors.toList());
            new ObjectMapper().writeValue(resp.getWriter(), cities);

        } else if ("/api/weather".equals(path)) {
            // 获取天气信息并返回
            String cityId = req.getParameter("cityId");
            WeatherData weatherData = WeatherService.fetchWeather(cityId);
            new ObjectMapper().writeValue(resp.getWriter(), weatherData);
        }
    }
}
