package org.spring.groupAir.movie.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Data
@Builder
public class MovieInfo {

  private String movieCd;
  private String movieNm;
  private String movieNmEn;
  private String movieNmOg;
  private String showTm;
  private String prdtYear;
  private String openDt;
  private String prdtStatNm;
  private String typeNm;
  private List<Nations> nations;
  private List<Genres> genres;
  private List<Directors> directors;
  private List<Actors> actors;
  private List<ShowTypes> showTypes;
  private List<Companys> companys;
  private List<Audits> audits;
  private List<Staffs> staffs;

  // 기본 생성자
  public MovieInfo() {
  }

  // 모든 필드를 포함하는 생성자
  @JsonCreator
  public MovieInfo(
      @JsonProperty("movieCd") String movieCd,
      @JsonProperty("movieNm") String movieNm,
      @JsonProperty("movieNmEn") String movieNmEn,
      @JsonProperty("movieNmOg") String movieNmOg,
      @JsonProperty("showTm") String showTm,
      @JsonProperty("prdtYear") String prdtYear,
      @JsonProperty("openDt") String openDt,
      @JsonProperty("prdtStatNm") String prdtStatNm,
      @JsonProperty("typeNm") String typeNm,
      @JsonProperty("nations") List<Nations> nations,
      @JsonProperty("genres") List<Genres> genres,
      @JsonProperty("directors") List<Directors> directors,
      @JsonProperty("actors") List<Actors> actors,
      @JsonProperty("showTypes") List<ShowTypes> showTypes,
      @JsonProperty("companys") List<Companys> companys,
      @JsonProperty("audits") List<Audits> audits,
      @JsonProperty("staffs") List<Staffs> staffs
  ) {
    this.movieCd = movieCd;
    this.movieNm = movieNm;
    this.movieNmEn = movieNmEn;
    this.movieNmOg = movieNmOg;
    this.showTm = showTm;
    this.prdtYear = prdtYear;
    this.openDt = openDt;
    this.prdtStatNm = prdtStatNm;
    this.typeNm = typeNm;
    this.nations = nations;
    this.genres = genres;
    this.directors = directors;
    this.actors = actors;
    this.showTypes = showTypes;
    this.companys = companys;
    this.audits = audits;
    this.staffs = staffs;
  }
}
