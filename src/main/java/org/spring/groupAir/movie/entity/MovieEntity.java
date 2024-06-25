package org.spring.groupAir.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "movie")
public class MovieEntity {

    @Id
    @Column(name = "movie_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieCd;
    private String movieNm;
    private String movieNmEn;
    private String prdtYear;
    private String repGenreNm;
    private String repNationNm;
    private String typeNm;
    private String getCompanyCd;
    private String getCompanyNm;
}
