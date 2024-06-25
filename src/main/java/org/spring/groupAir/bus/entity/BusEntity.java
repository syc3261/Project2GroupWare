package org.spring.groupAir.bus.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "bus")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusEntity {

    @Id
    @Column(name = "bus_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String busRouteNm;  // 버스 노선
    private String routeType; // 타입
    private String stStationNm;  //기점
    private String edStationNm; //종점
    private String firstBusTm; //첫차
    private String lastLowTm; // 막차
    private String term; // 배차시간
    private String busRouteId; // 버스 기본 ID
    private String corpNm;  // 버스 회사 정보

}