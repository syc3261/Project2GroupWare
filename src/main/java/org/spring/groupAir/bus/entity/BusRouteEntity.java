package org.spring.groupAir.bus.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bus_route")
@Entity
public class BusRouteEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String busType; // 차량 유형
    private String busRouteId;
    private String congetion; // 혼잡도
    private String gpsX; // x좌표
    private String gpsY; // y 좌표
    private String posX; // X 좌표
    private String posY; // y 좌표
    private String isFullFlag;
    private String islastyn; //막차 여부
    private String isrunyn; // 운행 여부
    private String lastStTm; // 종점 도착 소요시간
    private String lastStnId; // 최종 정류소 고유 아이디
    private String nextStId; // 다음 정류소 아이디
    private String nextStNm; // 다음 정류소 이름
    private String nextStTm; // 다음 정류소 도착소요시간
    private String plainNo; // 차량 번호
    private String stopFlag; // 정류소 도착 여부
    private String vehId; // 차량 ID
}