package com.example.weather.Service;

import com.example.weather.VO.WeatherResponse;

public interface WeatherDataService {

    WeatherResponse getDataByCityId(String cityId);


    WeatherResponse getDataByCityName(String cityName);

}
