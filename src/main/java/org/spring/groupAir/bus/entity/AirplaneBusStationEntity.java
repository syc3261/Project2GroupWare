package org.spring.groupAir.bus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "airplaneBusStation")
public class AirplaneBusStationEntity {
    @Id
    @Column(name = "airplane_bus_station_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String routeinfo; // 노선정보

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_bus_id")
    private AirplaneBusEntity airplaneBusEntity;
}

