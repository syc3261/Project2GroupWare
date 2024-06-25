package org.spring.groupAir.weather.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherDto {

    private Coord coord;

    private List<Weather> weather;

    private String base;

    private Main main;

    private String visibility;

    private Wind wind;

    private Clouds clouds;

    private String dt;

    private Sys sys;

    private String timezone;

    private String id;

    private String name;

    private String cod;

    private Rain rain;

//    private String pop;
}
