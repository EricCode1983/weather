package com.example.weather.VO;

import lombok.Data;

import java.io.Serializable;

@Data
public class Forecast implements Serializable {

    private static final long serialVersionUID = 1L;
    private String date;
    private String high;
    private String fengli;
    private String low;
    private String fengxiang;
    private String type;


}
