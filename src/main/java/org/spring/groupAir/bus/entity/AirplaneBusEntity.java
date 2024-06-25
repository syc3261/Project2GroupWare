package org.spring.groupAir.bus.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "airplaneBus")
public class AirplaneBusEntity {
    @Id
    @Column(name = "airplane_bus_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String area; // 지역

    private String busnumber; // 버스 번호

    private String toawfirst; // 공항행 첫차

    private String toawlast; // 공항행 막차

    private String t1endfirst; // T1종점행첫차

    private String t2endfirst; // T2종점행첫차

    private String t1endlast; // T1종점행막차

    private String t2endlast; // T2종점행막차

    private String adultfare; // 성인 요금

    private String busclass; // 버스 등급

    private String cpname; // 운수회사명

    private String t1ridelo; // T1승차위치

    private String t2ridelo; // T2승차위치

    @OneToMany(mappedBy = "airplaneBusEntity"
        , fetch = FetchType.LAZY
        , cascade = CascadeType.ALL)
    private List<AirplaneBusStationEntity> airplaneBusStationEntityList;
}
