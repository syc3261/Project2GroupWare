package org.spring.groupAir.weather.dto;

import lombok.Data;

@Data
public class Sys {

    private String type;
    private String id;
    private String country;
    private String sunrise;
    private String sunset;

    private String pod;
}
