package org.spring.groupAir.movie.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "moviePoster")
public class MoviePosterEntity {

  @Id
  @Column(name="moviePoster_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  private String movieTitle;
  private String releaseDate;
  private String posterUrl;

  @Column(name = "`rank`")
  private String rank;
  private String movieCd;
  private String movieNm;
  private String openDt;
  private String salesAmt; // 매출액
  private String audiAcc; // 누적 관객 수

}
