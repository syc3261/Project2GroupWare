package org.spring.groupAir.bus.dto.airplaneBus;

import lombok.Data;

@Data
public class Items {
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

    private String t1wdayt; // T1평일시간표

    private String t1wt; // T1주말시간표

    private String t2wdayt; // T2평일시간표

    private String t2wt; // T2주말시간표

    private String routeinfo; // 노선정보

    private String t1ridelo; // T1승차위치

    private String t2ridelo; // T2승차위치
}
