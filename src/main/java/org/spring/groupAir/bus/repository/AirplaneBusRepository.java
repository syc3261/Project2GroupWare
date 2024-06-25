package org.spring.groupAir.bus.repository;

import org.spring.groupAir.bus.entity.AirplaneBusEntity;
import org.spring.groupAir.bus.entity.AirplaneBusStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirplaneBusRepository extends JpaRepository<AirplaneBusEntity, Long> {
    Optional<AirplaneBusEntity> findByBusnumber(String busNumber);

}
