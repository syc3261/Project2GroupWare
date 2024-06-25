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
@Entity(name = "BoxOffice")
public class BoxOfficeEntity {

    @Id
    @Column(name = "BoxOffice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieCd;
    private String movieNm;

    @Column(name = "`rank`")  // 백틱을 사용하여 예약어 처리
    private String rank;
    private String rankInten;
    private String rankOldAndNew;
    private String audiCnt;
    private String salesAcc;
}
