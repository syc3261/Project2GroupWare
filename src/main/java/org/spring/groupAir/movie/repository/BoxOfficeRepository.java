package org.spring.groupAir.movie.repository;


import org.spring.groupAir.movie.entity.BoxOfficeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BoxOfficeRepository extends JpaRepository<BoxOfficeEntity,Long> {


    Optional<BoxOfficeEntity> findByMovieCd(String movieCd);

    Optional<BoxOfficeEntity> findByRank(String name);
}

