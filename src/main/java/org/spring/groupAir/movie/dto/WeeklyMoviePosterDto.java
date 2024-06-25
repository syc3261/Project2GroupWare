package org.spring.groupAir.movie.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Builder
@Data
public class WeeklyMoviePosterDto {
  private Long id;

  private String movieTitle;
  private String releaseDate;
  private String posterUrl;

  private String rank;
  private String movieCd;
  private String movieNm;
  private String rankInten;
  private String openDt;
  private String rankOldAndNew;
  private String audiCnt;
  private String salesAcc;


}
