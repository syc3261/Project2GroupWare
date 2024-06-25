package org.spring.groupAir.controller;

import lombok.RequiredArgsConstructor;
import org.spring.groupAir.airplane.service.AirplaneService;
import org.spring.groupAir.board.service.BoardService;
import org.spring.groupAir.commute.dto.CommuteDto;
import org.spring.groupAir.commute.dto.VacationDto;
import org.spring.groupAir.commute.service.CommuteService;
import org.spring.groupAir.commute.service.VacationService;
import org.spring.groupAir.config.MyUserDetailsImpl;
import org.spring.groupAir.controller.utill.ApiUtill;
import org.spring.groupAir.department.dto.TopDepartmentDto;
import org.spring.groupAir.department.service.DepartmentService;
import org.spring.groupAir.department.service.TopDepartmentService;
import org.spring.groupAir.member.dto.MemberDto;
import org.spring.groupAir.member.service.MemberService;
import org.spring.groupAir.schedule.dto.ScheduleDto;
import org.spring.groupAir.schedule.service.ScheduleService;
import org.spring.groupAir.sign.service.SignService;
import org.spring.groupAir.weather.repository.WeatherRepository;
import org.spring.groupAir.weather.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final AirplaneService airplaneService;
    private final MemberService memberService;
    private final CommuteService commuteService;
    private final ScheduleService scheduleService;
    private final BoardService boardService;
    private final VacationService vacationService;
    private final TopDepartmentService topDepartmentService;
    private final DepartmentService departmentService;
    private final SignService signService;


    @GetMapping({"/", "/index"})
    public String index(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "index";
    }

    @GetMapping("/role/admin")
    public String adminPage(Model model) {


        airplaneService.updateStatus();
        airplaneService.deleteOverTimeAirplane();
        vacationService.findVacationPerson();
        vacationService.deleteOverTimeVacation();
        commuteService.notWorkOut();
        commuteService.notWorkIn();

        int sickVacationPeople = vacationService.sickVacationPeople();
        int vacationPeople = vacationService.vacationPeople();

        int latePeople = commuteService.latePeople();
        int leaveEarlyPeople = commuteService.leaveEarlyPeople();
        int workPeople = commuteService.workPeople();
        int workOutPeople = commuteService.workOutPeople();
        int notWorkInPeople = commuteService.notWorkInPeople();

        int todayAirplane = airplaneService.findTodayAirplane();
        int allAirplane = airplaneService.findAllAirplane();

        int managers = memberService.selectPilot().size();
        int members = memberService.countMember();

        int board1 = boardService.board1();
        int board2 = boardService.board2();
        int board3 = boardService.board3();
        int board4 = boardService.board4();


        List<ScheduleDto> scheduleDtoList = scheduleService.todayAllSchedule();
        TopDepartmentDto topDepartmentDto = new TopDepartmentDto();
        List<TopDepartmentDto> topDepartmentDtos = topDepartmentService.List(topDepartmentDto);

        model.addAttribute("sickVacationPeople", sickVacationPeople);
        model.addAttribute("vacationPeople", vacationPeople);

        model.addAttribute("latePeople", latePeople);
        model.addAttribute("leaveEarlyPeople", leaveEarlyPeople);
        model.addAttribute("workPeople", workPeople);
        model.addAttribute("workOutPeople", workOutPeople);
        model.addAttribute("notWorkInPeople", notWorkInPeople);

        model.addAttribute("todayAirplane", todayAirplane);
        model.addAttribute("allAirplane", allAirplane);

        model.addAttribute("managers", managers);
        model.addAttribute("members", members);

        model.addAttribute("board1", board1);
        model.addAttribute("board2", board2);
        model.addAttribute("board3", board3);
        model.addAttribute("board4", board4);

//        model.addAttribute("departmentDtoList", departmentDtoList);
        model.addAttribute("topDepartmentDtos", topDepartmentDtos);

        model.addAttribute("scheduleDtoList", scheduleDtoList);

        return "role/admin";
    }

    @GetMapping("/role/manager")
    public String managerPage(@AuthenticationPrincipal MyUserDetailsImpl myUserDetails, Model model) {


        airplaneService.updateStatus();
        airplaneService.deleteOverTimeAirplane();
        vacationService.findVacationPerson();
        vacationService.deleteOverTimeVacation();
        commuteService.notWorkOut();
        commuteService.notWorkIn();

        List<CommuteDto> commuteDtoList = commuteService.commuteList(myUserDetails.getMemberEntity().getId());
        int boardCount = boardService.myBoardCount(myUserDetails.getMemberEntity().getId());
        int todayMyAirplaneCount = airplaneService.todayMyAirplaneCount(myUserDetails.getMemberEntity().getId());
        int myAirplaneCount = airplaneService.myAirplanes(myUserDetails.getMemberEntity().getId());
        MemberDto memberDto = memberService.memberDetail(myUserDetails.getMemberEntity().getId());

        Long deId = myUserDetails.getMemberEntity().getDepartmentEntity().getId();
        List<MemberDto> list = departmentService.getMembers(deId);

        List<ScheduleDto> scheduleDtoList = scheduleService.todayMySchedule(myUserDetails.getMemberEntity().getId());

        int notSign = signService.notSignCount(myUserDetails.getMemberEntity().getName());
        int signOk = signService.getAllSignOk(myUserDetails.getMemberEntity().getName()).size();
        int signNo = signService.getAllSignNo(myUserDetails.getMemberEntity().getName()).size();

        model.addAttribute("list", list);
        model.addAttribute("commuteDtoList", commuteDtoList);
        model.addAttribute("boardCount", boardCount);
        model.addAttribute("myAirplaneCount", myAirplaneCount);
        model.addAttribute("todayMyAirplaneCount", todayMyAirplaneCount);
        model.addAttribute("memberDto", memberDto);
        model.addAttribute("scheduleDtoList", scheduleDtoList);

        model.addAttribute("notSign", notSign);
        model.addAttribute("signOk", signOk);
        model.addAttribute("signNo", signNo);

        return "role/manager";
    }

    @GetMapping("/role/member")
    public String memberPage(@AuthenticationPrincipal MyUserDetailsImpl myUserDetails, Model model) {


        airplaneService.updateStatus();
        airplaneService.deleteOverTimeAirplane();
        vacationService.findVacationPerson();
        vacationService.deleteOverTimeVacation();
        commuteService.notWorkOut();
        commuteService.notWorkIn();

        List<CommuteDto> commuteDtoList = commuteService.commuteList(myUserDetails.getMemberEntity().getId());
        int boardCount = boardService.myBoardCount(myUserDetails.getMemberEntity().getId());
        MemberDto memberDto = memberService.memberDetail(myUserDetails.getMemberEntity().getId());

        List<VacationDto> vacationDtoList = vacationService.myVacation(myUserDetails.getMemberEntity().getId());


        List<ScheduleDto> scheduleDtoList = scheduleService.todayMySchedule(myUserDetails.getMemberEntity().getId());

        int myNotSignCount = signService.myNotSignCount(myUserDetails.getMemberEntity().getId());
        int mySignOkCount = signService.mySignOkCount(myUserDetails.getMemberEntity().getId());
        int mySignNoCount = signService.mySignNoCount(myUserDetails.getMemberEntity().getId());

        Long deId = myUserDetails.getMemberEntity().getDepartmentEntity().getId();
        List<MemberDto> list = departmentService.getMembers(deId);

        model.addAttribute("list", list);

        model.addAttribute("commuteDtoList", commuteDtoList);
        model.addAttribute("boardCount", boardCount);
        model.addAttribute("memberDto", memberDto);
        model.addAttribute("vacationDtoList", vacationDtoList);
        model.addAttribute("scheduleDtoList", scheduleDtoList);

        model.addAttribute("myNotSignCount", myNotSignCount);
        model.addAttribute("mySignOkCount", mySignOkCount);
        model.addAttribute("mySignNoCount", mySignNoCount);

        return "role/member";
    }

    // menuBar 날씨 정보
    private final WeatherService weatherService;

    @GetMapping("/weather")
    public ResponseEntity<Map<String, String>> wData(String q) {

        String appId = "745e1514332e9aa9afb7b3c1507c7cfc";

        String appKey = "https://api.openweathermap.org/data/2.5/weather?q=" + q + "&appid=" + appId + "&units=metric&lang=kr";

        // 번호로 검색 id
//        https://api.openweathermap.org/data/2.5/weather?id=1835848&appid=745e1514332e9aa9afb7b3c1507c7cfc&lang=kr
        // 이름으로 검색
//        https://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=745e1514332e9aa9afb7b3c1507c7cfc&lang=kr

        Map<String, String> weather = new HashMap<>();

        Map<String, String> request = new HashMap<>();
        request.put("Content-type", "application/json");

        String response = ApiUtill.get(appKey, request);

        weatherService.insertDB(response);

        weather.put("weather", response);

        return ResponseEntity.status(HttpStatus.OK).body(weather);
    }

    // 날씨 API ( 다음날까지 날씨 )
    @GetMapping("/forecast")
    public ResponseEntity<Map<String, String>> apiData(String q) {

        String appId = "745e1514332e9aa9afb7b3c1507c7cfc";

        String appKey = "https://api.openweathermap.org/data/2.5/forecast?q=" + q + "&appid=" + appId + "&cnt=16&units=metric&lang=kr";

        Map<String, String> request = new HashMap<>();
        request.put("Content-type", "application/json");

        String response = ApiUtill.get(appKey, request);

        weatherService.insertDBList(response);

        Map<String, String> weather = new HashMap<>();
        weather.put("weatherList", response);


        return ResponseEntity.status(HttpStatus.OK).body(weather);
    }



}
