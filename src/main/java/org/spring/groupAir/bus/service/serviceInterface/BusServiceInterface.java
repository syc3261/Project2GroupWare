package org.spring.groupAir.bus.service.serviceInterface;

import org.spring.groupAir.bus.dto.airplaneBus.AirplaneBusStationDto;

import java.util.List;

public interface BusServiceInterface {
    void insertResponseBody(String rs);

    void busRouteInfo(String rs, String busRouteId);

    void insertAirplaneBus(String responseBody);

    List<AirplaneBusStationDto> findAirplaneBusRoute(String busNumber);
}
