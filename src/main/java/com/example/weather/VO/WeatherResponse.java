package com.example.weather.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class WeatherResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private Weather data;
    private Integer status;
    private String desc;





}