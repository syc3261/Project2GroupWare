package org.spring.groupAir.weather.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Main {

    private String temp;

    private String feels_like;

    private String temp_min;

    private String temp_max;

    private String pressure;

    private String humidity;

    private String sea_level;

    private String grnd_level;

    private String temp_kf;

}
