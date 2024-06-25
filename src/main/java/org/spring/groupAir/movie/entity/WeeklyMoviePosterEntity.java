package org.spring.groupAir.movie.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "weeklyMoviePoster")
public class WeeklyMoviePosterEntity {

  @Id
  @Column(name="weeklyMoviePoster_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String movieTitle;
  private String releaseDate;
  private String posterUrl;

  @Column(name = "`rank`")
  private String rank;
  private String movieCd;
  private String movieNm;
  private String rankInten;
  private String openDt;
  private String rankOldAndNew;
  private String audiCnt;
  private String salesAcc;


}
