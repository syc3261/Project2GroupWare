package org.spring.groupAir.movie.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovieListResult {

    private String totCnt;
    private String source;
    private List<MovieList> movieList;

}
