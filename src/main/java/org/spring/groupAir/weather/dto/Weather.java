package org.spring.groupAir.weather.dto;

import lombok.Data;

@Data
public class Weather {

    private String id;
    private String main;
    private String description;
    private String icon;


}
