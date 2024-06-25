package org.spring.groupAir.weather.entity;

import lombok.*;
import org.spring.groupAir.weather.dto.Main;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "weather_tb")
public class WeatherEntity {

    @Id
    @Column(name = "weather_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 도시이름

    private String lat; // 위도
    private String lon; // 경도

    private String temp; // 현재온도
    private String temp_min; // 최저온도
    private String temp_max; // 최고온도

}
