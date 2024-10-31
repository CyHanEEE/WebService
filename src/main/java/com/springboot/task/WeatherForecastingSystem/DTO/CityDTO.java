package com.springboot.task.WeatherForecastingSystem.DTO;

public class CityDTO {
    private String id;
    private String name;

    public CityDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

