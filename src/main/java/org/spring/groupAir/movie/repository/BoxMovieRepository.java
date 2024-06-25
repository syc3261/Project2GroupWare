package org.spring.groupAir.movie.repository;


import org.spring.groupAir.movie.entity.BoxMovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoxMovieRepository extends JpaRepository<BoxMovieEntity, Long> {
  Optional<BoxMovieEntity> findByMovieCd(String movieCd);
}
