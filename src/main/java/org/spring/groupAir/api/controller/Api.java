package org.spring.groupAir.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class Api {

    // 날씨 API 과제
    @GetMapping("/weather/weatherApi")
    public String weatherApi() {

        return "api/weather/weatherApi";
    }

    // 공항 일주일 날씨예보
    @GetMapping("/weather/weatherWeek")
    public String weatherWeek() {

        return "api/weather/weatherWeek";
    }

    @GetMapping("/bus/index")
    public String busApi() {

        return "api/bus/index";
    }
    @GetMapping("/bus/airplaneBusIndex")
    public String airplaneBusApi() {

        return "api/bus/airplaneBusIndex";
    }

    @GetMapping("/movie/index")
    public String movieapi() {
        return "api/movie/index";
    }


}
