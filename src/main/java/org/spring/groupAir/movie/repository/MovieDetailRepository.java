package org.spring.groupAir.movie.repository;


import org.spring.groupAir.movie.entity.MovieDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MovieDetailRepository extends JpaRepository<MovieDetailEntity,Long> {
    Optional<MovieDetailEntity> findByMovieNm(String movieNm);
}

