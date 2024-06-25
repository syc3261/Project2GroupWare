package org.spring.groupAir.bus.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "bus_detail")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusDetailEntity {

    @Id
    @Column(name = "bus_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String busRouteId;

    private String beginTm;
    private String lastTm;
    private String busRouteNm;
    private String routeType;
    private String gpsX;
    private String gpsY;
    private String posX;
    private String posY;
    private String stationNm;
    private String stationNo;


}