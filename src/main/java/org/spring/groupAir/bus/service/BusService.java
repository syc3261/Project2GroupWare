package org.spring.groupAir.bus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.spring.groupAir.bus.dto.airplaneBus.AirplaneBusDto;
import org.spring.groupAir.bus.dto.airplaneBus.AirplaneBusStationDto;
import org.spring.groupAir.bus.dto.airplaneBus.Items;
import org.spring.groupAir.bus.dto.bus.BusDto;
import org.spring.groupAir.bus.dto.bus.BusResponse;
import org.spring.groupAir.bus.dto.bus.ItemList;
import org.spring.groupAir.bus.dto.busRoute.BusRouteResponse;
import org.spring.groupAir.bus.dto.busRoute.ItemRouteList;
import org.spring.groupAir.bus.dto.busStation.BusStationResponse;
import org.spring.groupAir.bus.dto.busStation.ItemStationList;
import org.spring.groupAir.bus.entity.*;
import org.spring.groupAir.bus.repository.*;
import org.spring.groupAir.bus.service.serviceInterface.BusServiceInterface;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BusService implements BusServiceInterface {
    private final BusRepository busRepository;
    private final BusDetailRepository busDetailRepository;
    private final BusRouteRepository busRouteRepository;
    private final AirplaneBusRepository airplaneBusRepository;
    private final AirplaneBusStationRepository airplaneBusStationRepository;
    private final BusMapperRepository busMapperRepository;

    public void insertResponseBody(String rs) {

        ObjectMapper objectMapper = new ObjectMapper();

        BusResponse response = null;
        try {
            // json 문자열데이터를 -> 클래스에 매핑
            response = objectMapper.readValue(rs, BusResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<ItemList> busItemList = response.getMsgBody().getItemList()
            .stream()
            .collect(Collectors.toList());

        System.out.println(" <<  busItemList " + busItemList);

        for (ItemList item : busItemList) {
            System.out.println("  busRouteId " + item.getBusRouteId());
            BusEntity busEntity = BusEntity.builder()
                .busRouteId(item.getBusRouteId())
                .busRouteNm(item.getBusRouteNm())
                .firstBusTm(item.getFirstBusTm())
                .lastLowTm(item.getLastLowTm())
                .term(item.getTerm())
                .corpNm(item.getCorpNm())
                .routeType(item.getRouteType())
                .edStationNm(item.getEdStationNm())
                .stStationNm(item.getStStationNm())
                .build();
            //JPA
//            Optional<BusEntity> optionalBusEntity
//                = busRepository.findByBusRouteId(item.getBusRouteId());
//
            //MyBatis
            Optional<BusEntity> optionalBusEntity = busMapperRepository.busList(item.getBusRouteId());

            if (!optionalBusEntity.isPresent()) {
                busRepository.save(busEntity);
            }
        }
    }

    @Override
    public void busRouteInfo(String rs, String busRouteId) {
        ObjectMapper objectMapper = new ObjectMapper();
        BusRouteResponse response = null;

        try {
            // json 문자열데이터를 -> 클래스에 매핑
            response = objectMapper.readValue(rs, BusRouteResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(" <<  BusStationResponse " + response);

        List<ItemRouteList> busRouteItemList = response.getMsgBody().getItemList()
            .stream()
            .collect(Collectors.toList());


        System.out.println(" <<  busRouteItemList " + busRouteItemList);

        for (ItemRouteList item : busRouteItemList) {

            BusRouteEntity busRouteEntity = BusRouteEntity.builder()
                .busRouteId(busRouteId)
                .busType(item.getBusType())
                .congetion(item.getCongetion())
                .gpsX(item.getGpsX())
                .gpsY(item.getGpsY())
                .posX(item.getPosX())
                .posY(item.getPosY())
                .isFullFlag(item.getIsFullFlag())
                .islastyn(item.getIslastyn())
                .isrunyn(item.getIsrunyn())
                .lastStTm(item.getLastStTm())
                .lastStnId(item.getLastStnId())
                .nextStId(item.getNextStId())
                .nextStTm(item.getNextStTm())
                .plainNo(item.getPlainNo())
                .stopFlag(item.getStopFlag())
                .vehId(item.getVehId())
                .build();

            //JPA
//            Optional<BusRouteEntity> optionalBusRouteEntity
//                = busRouteRepository.findByBusRouteId(busRouteId);

            //MyBatis
            Optional<BusRouteEntity> optionalBusRouteEntity
                = busMapperRepository.busRoute(busRouteId);

            if (!optionalBusRouteEntity.isPresent()) {
                busRouteRepository.save(busRouteEntity);
            }
        }
    }

    @Override
    public void insertAirplaneBus(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper(); // 객체로부터 Json 형태의 문자열을 만들어 준다.

        AirplaneBusDto airplaneBusDto = null;

        try {
            airplaneBusDto = objectMapper.readValue(responseBody, AirplaneBusDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Items> itemsList = airplaneBusDto.getResponse().getBody().getItems();

        for (Items items : itemsList) {
            AirplaneBusEntity airplaneBusEntity = AirplaneBusEntity.builder()
                .area(items.getArea())
                .busnumber(items.getBusnumber())
                .toawfirst(items.getToawfirst())
                .toawlast(items.getToawlast())
                .t1endfirst(items.getT1endfirst())
                .t2endfirst(items.getT2endfirst())
                .t1endlast(items.getT1endlast())
                .t2endlast(items.getT2endlast())
                .adultfare(items.getAdultfare())
                .busclass(items.getBusclass())
                .cpname(items.getCpname())
                .t1ridelo(items.getT1ridelo())
                .t2ridelo(items.getT2ridelo())
                .build();

//            //JPA
            Optional<AirplaneBusEntity> optionalAirplaneBusEntity = airplaneBusRepository.findByBusnumber(items.getBusnumber());

            //MyBatis
//            Optional<AirplaneBusEntity> optionalAirplaneBusEntity = busMapperRepository.airplaneBusFind(items.getBusnumber());

            if (!optionalAirplaneBusEntity.isPresent()) {
                airplaneBusEntity = airplaneBusRepository.save(airplaneBusEntity);
            } else {
                airplaneBusEntity = optionalAirplaneBusEntity.get();
            }

            //JPA
//            airplaneBusStationRepository.deleteByAirplaneBusEntity(airplaneBusEntity);

            //MyBatis
            busMapperRepository.deleteBusStation(airplaneBusEntity.getId());

            String routeinfo = items.getRouteinfo();
            if(routeinfo != null){
                List<String> stops = Arrays.asList(routeinfo.split(", "));
                // 새로운 정류장 정보 추가
                for (String stop : stops) {
                    AirplaneBusStationEntity airplaneBusStationEntity = AirplaneBusStationEntity.builder()
                        .airplaneBusEntity(airplaneBusEntity)
                        .routeinfo(stop)
                        .build();
                    airplaneBusStationRepository.save(airplaneBusStationEntity);
                }
            }
        }
    }

    @Override
    public List<AirplaneBusStationDto> findAirplaneBusRoute(String busNumber) {

        AirplaneBusEntity airplaneBusEntity = airplaneBusRepository.findByBusnumber(busNumber).get();

        //JPA
//        List<AirplaneBusStationEntity> airplaneBusStationEntityList
//            = airplaneBusStationRepository.findByAirplaneBusEntityId(airplaneBusEntity.getId());

        //MyBatis
        List<AirplaneBusStationEntity> airplaneBusStationEntityList
            = busMapperRepository.airplaneBusStation(airplaneBusEntity.getId());

        List<AirplaneBusStationDto> airplaneBusStationDtoList
            = airplaneBusStationEntityList.stream().map(busStationEntity ->
            AirplaneBusStationDto.builder()
                .id(busStationEntity.getId())
                .routeinfo(busStationEntity.getRouteinfo())
                .airplaneBusEntity(busStationEntity.getAirplaneBusEntity())
                .build()
        ).collect(Collectors.toList());

        return airplaneBusStationDtoList;
    }

    public void busStationPostdo(String rs) {

        System.out.println(rs + " rs2");
        ObjectMapper objectMapper = new ObjectMapper();
        BusStationResponse response = null;

        try {
            // json 문자열데이터를 -> 클래스에 매핑
            response = objectMapper.readValue(rs, BusStationResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(" <<  BusStationResponse " + response);

        List<ItemStationList> busStationItemList = response.getMsgBody().getItemList()
            .stream()
            .collect(Collectors.toList());

        System.out.println(" <<  busStationItemList " + busStationItemList);

        for (ItemStationList item : busStationItemList) {
            System.out.println("  getStationNm " + item.getStationNm());

            BusDetailEntity busDetailEntity = BusDetailEntity.builder()
                .busRouteId(item.getBusRouteId())
                .beginTm(item.getBeginTm())
                .lastTm(item.getLastTm())
                .busRouteNm(item.getBusRouteNm())
                .routeType(item.getRouteType())
                .gpsX(item.getGpsX())
                .gpsY(item.getGpsY())
                .posX(item.getPosX())
                .posY(item.getPosY())
                .stationNm(item.getStationNm())
                .stationNo(item.getStationNo())
                .build();

            //JPA
//            Optional<BusDetailEntity> optionalBusDetailEntity
//                = busDetailRepository.findByBusRouteId(item.getBusRouteId());

            //MyBatis
            Optional<BusDetailEntity> optionalBusDetailEntity
                = busMapperRepository.busDetail(item.getBusRouteId());

            if (!optionalBusDetailEntity.isPresent()) {

                System.out.println(" save+");
                busDetailRepository.save(busDetailEntity);
            }
        }
    }
}
