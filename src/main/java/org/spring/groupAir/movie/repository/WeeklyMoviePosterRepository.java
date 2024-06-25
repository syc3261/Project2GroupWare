package org.spring.groupAir.movie.repository;

import org.spring.groupAir.movie.entity.MovieDetailEntity;
import org.spring.groupAir.movie.entity.MoviePosterEntity;
import org.spring.groupAir.movie.entity.WeeklyMoviePosterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeeklyMoviePosterRepository extends JpaRepository<WeeklyMoviePosterEntity, Long> {
    Optional<WeeklyMoviePosterEntity> findByMovieNm(String name);


//    Optional<WeeklyMoviePosterEntity> findByRank(String );
}
