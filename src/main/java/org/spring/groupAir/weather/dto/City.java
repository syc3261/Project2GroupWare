package org.spring.groupAir.weather.dto;

import lombok.Data;

@Data
public class City {

    private String id;
    private String name;
    private Coord coord;
    private String country;
    private String population;
    private String timezone;
    private String sunrise;
    private String sunset;
}
