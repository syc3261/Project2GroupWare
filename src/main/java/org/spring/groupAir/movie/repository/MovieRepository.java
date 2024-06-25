package org.spring.groupAir.movie.repository;


import org.spring.groupAir.movie.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    Optional<MovieEntity> findByMovieCd(String movieCd);






}
