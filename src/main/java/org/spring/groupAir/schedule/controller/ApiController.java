package org.spring.groupAir.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.spring.groupAir.config.MyUserDetailsImpl;
import org.spring.groupAir.schedule.dto.ScheduleDto;
import org.spring.groupAir.schedule.service.ScheduleService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
@RequiredArgsConstructor
public class ApiController {

  private final ScheduleService scheduleService;


  @GetMapping("/events")
  public List<ScheduleDto> eventsCalendar() {
    List<ScheduleDto> scheduleDtoList = scheduleService.scheduleListAll();

    return scheduleDtoList;
  }


  // 일정 저장
  @PostMapping("/calendar")

  public List<ScheduleDto> setCalendar(@ModelAttribute ScheduleDto scheduleDto,
                                       @AuthenticationPrincipal MyUserDetailsImpl myUserDetails){

    scheduleDto.setEmployeeId(myUserDetails.getMemberEntity().getId());

    scheduleService.setCalendar(scheduleDto);

    return scheduleService.scheduleListAll();
  }
  @PostMapping("/calendar/{id}")

  public List<ScheduleDto> setCalendar(@PathVariable("id") Long id,
      @ModelAttribute ScheduleDto scheduleDto,
                                       @AuthenticationPrincipal MyUserDetailsImpl myUserDetails){

    scheduleDto.setEmployeeId(myUserDetails.getMemberEntity().getId());

    List<ScheduleDto> scheduleDtoList = scheduleService.mySchedule(id);

    return scheduleDtoList;
  }

  @DeleteMapping("/calendar/{id}")
  public void deleteCalendar(@PathVariable("id")Long id){
    scheduleService.deleteSchedule(id);
  }

  @GetMapping("/calendar")
  public List<ScheduleDto> getCalendar() {
    return scheduleService.scheduleListAll();
  }


/*  @PostMapping("/calendar")
  public List<ScheduleDto> setMyCalendar(@ModelAttribute ScheduleDto scheduleDto,
                                       @AuthenticationPrincipal MyUserDetailsImpl myUserDetails){

    scheduleDto.setEmployeeId(myUserDetails.getMemberEntity().getId());

    scheduleService.setCalendar(scheduleDto);

    // 사용자의 일정만을 조회하여 반환
    return scheduleService.getScheduleByEmployeeId(myUserDetails.getMemberEntity().getId());
  }*/


  @GetMapping("/calendar/{employeeId}")
  public List<ScheduleDto> getEmployeeCalendar(@PathVariable Long employeeId) {

    return scheduleService.getScheduleByEmployeeId(employeeId);
  }



}

