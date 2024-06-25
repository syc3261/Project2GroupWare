package org.spring.groupAir.bus.dto.airplaneBus;

import lombok.*;
import org.spring.groupAir.bus.entity.AirplaneBusEntity;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AirplaneBusStationDto {

    private Long id;

    private String routeinfo;

    private AirplaneBusEntity airplaneBusEntity;

}
