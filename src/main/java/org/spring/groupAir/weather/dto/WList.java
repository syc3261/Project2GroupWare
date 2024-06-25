package org.spring.groupAir.weather.dto;

import lombok.Data;

import java.util.List;

@Data
public class WList {

    private String dt;
    private Main main;
    private List<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private String visibility;
    private String pop;
    private Rain rain;
    private Sys sys;
    private String dt_txt;
}
