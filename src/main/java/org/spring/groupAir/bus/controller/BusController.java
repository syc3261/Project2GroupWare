package org.spring.groupAir.bus.controller;

import lombok.RequiredArgsConstructor;
import org.spring.groupAir.bus.dto.airplaneBus.AirplaneBusStationDto;
import org.spring.groupAir.bus.service.BusService;
import org.spring.groupAir.controller.utill.ApiExplorer;
import org.spring.groupAir.movie.util.OpenApiUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
//@Controller
public class BusController {
    private final BusService busService;

    @GetMapping("/busList")
    public Map<String, String> busList(@RequestParam(required = false) String strSrch)
        throws IOException {
        // 공공데이터 포털 제공 API
        String rs = ApiExplorer.getBusList(strSrch);

        busService.insertResponseBody(rs);

        Map<String, String> map = new HashMap<>();

        map.put("rs", rs);
        return map;
    }


    @GetMapping("/busStationPost")
    public Map<String, String> busGet(@RequestParam(required = false) String busRouteId) throws IOException {

        String rs = ApiExplorer.getRespose(busRouteId);

        System.out.println("rs : " + rs);

        busService.busStationPostdo(rs);

        Map<String, String> map = new HashMap<>();
        map.put("rs", rs);
        return map;
    }

    @GetMapping("/busRouteInfo")
    public Map<String, String> busRouteInfo(@RequestParam(required = false) String busRouteId) throws IOException {

        String rs = ApiExplorer.getBusRoute(busRouteId);

        System.out.println("rs : " + rs);

        busService.busRouteInfo(rs,busRouteId);

        Map<String, String> map = new HashMap<>();
        map.put("rs", rs);
        return map;
    }

    @GetMapping("/airplaneBusList")
    public ResponseEntity<Map<String, Object>> busList(int q) {

        String appKey = "dXiOaPTv%2B%2BmtxN%2BBvSbXkVYM94MJMYArMLGBR2HWh7oBkSAZCOcajnFsveNRYFnV3aAK57W8jXedpwiE69EFsg%3D%3D";


        String apiUrl = " https://apis.data.go.kr/B551177/BusInformation/getBusInfo?serviceKey=" + appKey + "&area=" + q + "&numOfRows=1000&pageNo=1&type=json";


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-type", "application/json");


        // OpenAi Data -> GET
        String responseBody = OpenApiUtil.get(apiUrl, requestHeaders);

        // 1. DB에 저장
        busService.insertAirplaneBus(responseBody);

        // View return
        Map<String, Object> busList = new HashMap<>();
        System.out.println("responseBody>>>>>" + responseBody);
        busList.put("airplaneBusList", responseBody);


        return ResponseEntity.status(HttpStatus.OK).body(busList);
    }

    @GetMapping("/airplaneBusStationPost")
    public Map<String, Object> airplaneBusStationPost(@RequestParam(required = false) String busNumber) {


        List<AirplaneBusStationDto> airplaneBusStationDtoList = busService.findAirplaneBusRoute(busNumber);

        Map<String, Object> map = new HashMap<>();

        map.put("airplaneBusRoute", airplaneBusStationDtoList);

        return map;
    }
}
