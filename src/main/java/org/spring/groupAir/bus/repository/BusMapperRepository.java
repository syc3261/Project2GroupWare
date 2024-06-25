package org.spring.groupAir.bus.repository;

import org.apache.ibatis.annotations.Mapper;
import org.spring.groupAir.bus.dto.bus.BusDto;
import org.spring.groupAir.bus.entity.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Mapper
public interface BusMapperRepository {
    Optional<BusEntity> busList(String busRouteId);

    Optional<BusRouteEntity> busRoute(String busRouteId);

//    Optional<AirplaneBusEntity> airplaneBusFind(String busnumber);

    Optional<BusDetailEntity> busDetail(String busRouteId);

    void deleteBusStation(Long id);

    List<AirplaneBusStationEntity> airplaneBusStation(Long id);
}
