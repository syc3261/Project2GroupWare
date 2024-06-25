package org.spring.groupAir.bus.repository;

import org.spring.groupAir.bus.entity.BusDetailEntity;
import org.spring.groupAir.bus.entity.BusRouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusRouteRepository extends JpaRepository<BusRouteEntity, Long> {
        Optional<BusRouteEntity> findByBusRouteId(String busRouteId);
}
