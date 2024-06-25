package org.spring.groupAir.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.spring.groupAir.schedule.dto.ScheduleDto;
import org.spring.groupAir.schedule.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/my", produces = "application/json")
@RequiredArgsConstructor
public class ScheduleController {

  private final ScheduleService scheduleService;

  @GetMapping("/schedule")
  public String schedule() {
    return "calendar/myCalendar";
  }

  //----------------------------------------------------------//
  @GetMapping("/schedule/{employeeId}")
  public String mySchedule(@PathVariable("employeeId") Long employeeId, Model model) {

    List<ScheduleDto> scheduleDtoList = scheduleService.mySchedule(employeeId);

    model.addAttribute("scheduleDtoList", scheduleDtoList);

//    System.out.println("테스트확인: >>>" + scheduleDtoList.get(1).getContent());
//    System.out.println("테스트확인: >>>" + scheduleDtoList.get(2).getContent());

    return "calendar/mySchedule";
  }

}
