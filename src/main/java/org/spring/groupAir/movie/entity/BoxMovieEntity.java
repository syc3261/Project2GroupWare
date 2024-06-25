package org.spring.groupAir.movie.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "boxMovie")
public class BoxMovieEntity {

  @Id
  @Column(name="boxMovie_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "`rank`") // 백틱을 사용하여 예약어 처리
  private String rank;

  //  private String rank;
  private String movieCd;
  private String movieNm;
  private String openDt;
  private String salesAmt; // 매출액
  private String audiAcc; // 누적 관객 수


}
