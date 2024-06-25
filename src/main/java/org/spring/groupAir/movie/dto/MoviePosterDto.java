package org.spring.groupAir.movie.dto;

import lombok.Data;

@Data
public class MoviePosterDto {


  private Long id;

  private String movieTitle;
  private String releaseDate;
  private String posterUrl;

  private String movieCd;
  private String movieNm;
  private String openDt;
  private String salesAmt; // 매출액
  private String audiAcc; // 누적 관객 수


}
