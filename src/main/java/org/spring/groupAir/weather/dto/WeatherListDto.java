package org.spring.groupAir.weather.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherListDto {

    private String cod;

    private String message;

    private String cnt;

    private List<WList> list;

    private String dt_txt;

    private City city;

}
