package org.spring.groupAir.movie.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovieList {
    private String movieCd;
    private String movieNm;
    private String movieNmEn;
    private String prdtYear;
    private String openDt;
    private String typeNm;
    private String prdtStatNm;
    private String nationAlt;
    private String genreAlt;
    private String repNationNm;
    private String repGenreNm;
    private List<Directors> directors;
    private String peopleNm;
    private List<Companys> companys;
}
