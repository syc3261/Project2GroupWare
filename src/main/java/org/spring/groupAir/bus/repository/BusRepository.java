package org.spring.groupAir.bus.repository;

import org.spring.groupAir.bus.entity.BusEntity;
import org.spring.groupAir.movie.util.OpenApiUtil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusRepository extends JpaRepository<BusEntity, Long> {
    Optional<BusEntity> findByBusRouteId(String busRouteId);

    Optional<BusEntity> findByBusRouteNm(String busRouteNm);
}
