package org.spring.groupAir.bus.repository;

import org.spring.groupAir.bus.entity.AirplaneBusEntity;
import org.spring.groupAir.bus.entity.AirplaneBusStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirplaneBusStationRepository extends JpaRepository<AirplaneBusStationEntity, Long> {
    void deleteByAirplaneBusEntity(AirplaneBusEntity airplaneBusEntity);

    List<AirplaneBusStationEntity> findByAirplaneBusEntityId(Long id);
}
