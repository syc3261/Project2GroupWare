package org.spring.groupAir.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.groupAir.movie.dto.Nations;
import org.spring.groupAir.movie.dto.Staffs;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "movie_detail")
public class MovieDetailEntity {


    @Id
    @Column(name = "movie_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String movieCd; // 영화코드
    private String movieNm; // 영화이름
    private String movieNmEn;// 영화명(영어)
    private String openDt;// 개봉일
    private String prdtStatNm;// 개봉유무
    private String prdtYear;// 제작년도
    private String showTm;// 상영시간
    private String typeNm;// 유형
    private String source;// 주관

    //추가
    private String nationNm;
    private String directorsNm;
    private String peopleNm;
    private String staffNm;
}
