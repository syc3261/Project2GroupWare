package org.spring.groupAir.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.spring.groupAir.movie.dto.MovieInfo;
import org.spring.groupAir.movie.dto.WeeklyBoxOfficeList;
import org.spring.groupAir.bus.dto.bus.ItemList;
import org.spring.groupAir.weather.dto.Main;
import org.spring.groupAir.weather.dto.Weather;
import org.spring.groupAir.weather.dto.WeatherDto;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnswerDto {

    private Long id;

    private String content;

    private String keyword;

    private EmployeeInfo employee;

    private List<EmployeeInfo> employeeInfo;

    private Main weather;

    private List<Main> weatherTemp;


    private WeeklyBoxOfficeList weeklyBoxOffice;

    private List<WeeklyBoxOfficeList> weeklyBoxOfficeLists;

    private MovieInfo movieInfo;

    private List<MovieInfo> movieInfoList;

    private ItemList bus;

    private List<ItemList> busInfo;


    public AnswerDto employeeInfo(EmployeeInfo employee) {
        this.employee = employee;
        return this;
    }

    public AnswerDto weatherTemp(Main weather) {
        this.weather = weather;
        return this;
    }


    public AnswerDto weeklyMovie(WeeklyBoxOfficeList weeklyBoxOffice) {
        this.weeklyBoxOffice = weeklyBoxOffice;
        return this;
    }

    public AnswerDto weeklyMovieDetail(MovieInfo movieInfo) {
        this.movieInfo = movieInfo;
        return this;
    }


    public AnswerDto busInfo(ItemList bus) {
        this.bus = bus;
        return this;
    }

}
