package org.spring.groupAir.bus.repository;

import org.spring.groupAir.bus.entity.BusDetailEntity;
import org.spring.groupAir.controller.utill.ApiUtill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusDetailRepository extends JpaRepository<BusDetailEntity, Long> {
    Optional<BusDetailEntity> findByBusRouteId(String busRouteId);

    Optional<BusDetailEntity> findByStationNo(String nextStId);
}
