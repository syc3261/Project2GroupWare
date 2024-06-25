package org.spring.groupAir.movie.dto;

import lombok.Data;

import java.util.List;

@Data
public class BoxOfficeResult {

    private String boxofficeType;
    private String showRange;
    private String yearWeekTime;
    private List<WeeklyBoxOfficeList> weeklyBoxOfficeList;
    private List<DailyBoxOfficeList> dailyBoxOfficeList;
}
