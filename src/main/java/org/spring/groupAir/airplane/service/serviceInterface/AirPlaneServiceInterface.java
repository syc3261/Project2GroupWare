package org.spring.groupAir.airplane.service.serviceInterface;

import org.spring.groupAir.airplane.dto.AirplaneDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AirPlaneServiceInterface {
    Page<AirplaneDto> allAirplane(Pageable pageable, String subject, String search);

    void addAirplane(AirplaneDto airplaneDto);

    AirplaneDto airplaneDetail(Long id);

    void airplaneDelete(Long id);

    Page<AirplaneDto> myAirplane(Pageable pageable, Long id);

    Page<AirplaneDto> todayMyAirplane(Pageable pageable, Long id);

    void deleteOverTimeAirplane();

    AirplaneDto findAirplane(Long id);

    void airplaneUpdate(AirplaneDto airplaneDto);

    void updateStatus();

    int findTodayAirplane();

    int findAllAirplane();

    int todayMyAirplaneCount(Long id);

    int myAirplanes(Long id);
}
