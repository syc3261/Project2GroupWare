package org.spring.groupAir.movie.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class WeeklyBoxOfficeList {


    public WeeklyBoxOfficeList(String rnum, String rank, String rankInten, String rankOldAndNew, String movieCd, String movieNm, String openDt, String salesAmt, String salesShare, String salesInten, String salesChange, String salesAcc, String audiCnt, String audiInten, String audiChange, String audiAcc, String scrnCnt, String showCnt) {
        this.rnum = rnum;
        this.rank = rank;
        this.rankInten = rankInten;
        this.rankOldAndNew = rankOldAndNew;
        this.movieCd = movieCd;
        this.movieNm = movieNm;
        this.openDt = openDt;
        this.salesAmt = salesAmt;
        this.salesShare = salesShare;
        this.salesInten = salesInten;
        this.salesChange = salesChange;
        this.salesAcc = salesAcc;
        this.audiCnt = audiCnt;
        this.audiInten = audiInten;
        this.audiChange = audiChange;
        this.audiAcc = audiAcc;
        this.scrnCnt = scrnCnt;
        this.showCnt = showCnt;
    }

    @JsonCreator
    public WeeklyBoxOfficeList() {
        // 기본 생성자 내용 (옵션)

    }

    @JsonProperty("rnum")
    private String rnum;

    private String rank;

    @JsonProperty("rankInten")
    private String rankInten;

    @JsonProperty("rankOldAndNew")
    private String rankOldAndNew;
    @JsonProperty("movieCd")
    private String movieCd;

    @JsonProperty("movieNm")
    private String movieNm;

    @JsonProperty("openDt")
    private String openDt;

    @JsonProperty("salesAmt")
    private String salesAmt;

    @JsonProperty("salesShare")
    private String salesShare;

    @JsonProperty("salesInten")
    private String salesInten;

    @JsonProperty("salesChange")
    private String salesChange;

    @JsonProperty("salesAcc")
    private String salesAcc;

    @JsonProperty("audiCnt")
    private String audiCnt;

    @JsonProperty("audiInten")
    private String audiInten;

    @JsonProperty("audiChange")
    private String audiChange;

    @JsonProperty("audiAcc")
    private String audiAcc;

    @JsonProperty("scrnCnt")
    private String scrnCnt;

    @JsonProperty("showCnt")
    private String showCnt;

}
