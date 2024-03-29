package com.example.weather.Service;

import com.example.weather.VO.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherDataServiceImpl implements  WeatherDataService {

    private static final String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";

    private static final long TIME_OUT=10L;

    private final static Logger logger= LoggerFactory.getLogger(WeatherDataServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public WeatherResponse getDataByCityId(String cityId) {
        String uri = WEATHER_URI + "citykey=" + cityId;
        return this.doGetWeahter(uri);
    }

    @Override
    public WeatherResponse getDataByCityName(String cityName) {
        String uri = WEATHER_URI + "city=" + cityName;
        return this.doGetWeahter(uri);
    }

    private WeatherResponse doGetWeahter(String uri) {

        //Check cache first
        String key=uri;
        String strBody=null;
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse resp = null;
         ValueOperations<String,String> ops= stringRedisTemplate.opsForValue();
         logger.info(key);
        if(stringRedisTemplate.hasKey(key)){
            logger.info("Redis has data");
            strBody=ops.get(key);
        }else
        {

            logger.info("Redis dont has data");
            ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);
        if (respString.getStatusCodeValue() == 200) {
            //write data to cache
            strBody = respString.getBody();
        }
            ops.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);
        }

        try {
            resp = mapper.readValue(strBody, WeatherResponse.class);
        } catch (IOException e) {
            logger.error("Error!",e);
        }

        return resp;
    }
}
