package org.spring.groupAir.commute.controller;

import lombok.RequiredArgsConstructor;
import org.spring.groupAir.board.entity.BoardEntity;
import org.spring.groupAir.board.service.BoardService;
import org.spring.groupAir.commute.dto.CommuteDto;
import org.spring.groupAir.commute.dto.VacationDto;
import org.spring.groupAir.commute.service.CommuteService;
import org.spring.groupAir.commute.service.VacationService;
import org.spring.groupAir.department.entity.DepartmentEntity;
import org.spring.groupAir.member.dto.MemberDto;
import org.spring.groupAir.member.service.MemberService;
import org.spring.groupAir.salary.dto.SalaryDto;
import org.spring.groupAir.salary.entity.SalaryEntity;
import org.spring.groupAir.salary.service.SalaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/commute")
public class CommuteController {

    private final CommuteService commuteService;
    private final VacationService vacationService;
    private final MemberService memberService;
    private final SalaryService salaryService;
    private final BoardService boardService;


    @GetMapping({"", "/", "/index"})
    public String commuteIndex(Model model) {

        int sickVacationPeople = vacationService.sickVacationPeople();
        int vacationPeople = vacationService.vacationPeople();

        int latePeople = commuteService.latePeople();
        int leaveEarlyPeople = commuteService.leaveEarlyPeople();
        int workPeople = commuteService.workPeople();
        int workOutPeople = commuteService.workOutPeople();
        int notWorkInPeople = commuteService.notWorkInPeople();

        model.addAttribute("sickVacationPeople", sickVacationPeople);
        model.addAttribute("vacationPeople", vacationPeople);

        model.addAttribute("latePeople", latePeople);
        model.addAttribute("leaveEarlyPeople", leaveEarlyPeople);
        model.addAttribute("workPeople", workPeople);
        model.addAttribute("workOutPeople", workOutPeople);
        model.addAttribute("notWorkInPeople", notWorkInPeople);

        return "commute/index";
    }

    @GetMapping("/work")
    public String work(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       Model model,
                       @RequestParam(name = "subject", required = false) String subject,
                       @RequestParam(name = "search", required = false) String search) {

        Page<MemberDto> memberDtoPage = memberService.memberList(pageable, subject, search);

        int totalPage = memberDtoPage.getTotalPages();//전체page
        int newPage = memberDtoPage.getNumber();//현재page

        int blockNum = 3; //브라우저에 보이는 페이지 갯수

        int startPage = (int) ((Math.floor(newPage / blockNum) * blockNum) + 1 <= totalPage
            ? (Math.floor(newPage / blockNum) * blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum) - 1 < totalPage ? (startPage + blockNum) - 1 : totalPage;

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("memberDtoPage", memberDtoPage);

        return "commute/work";
    }

    @GetMapping("/workDetail/{id}")
    public String workDetail(@PathVariable("id") Long id, Model model) {
        List<CommuteDto> commuteDtoList = commuteService.commuteList(id);

        if(commuteDtoList.get(0).getTotalWork() != null){
            Duration allTotalWork = commuteService.totalWork(id);
            model.addAttribute("allTotalWork", allTotalWork);
        }

        commuteDtoList.sort(Comparator.comparing(CommuteDto::getId).reversed());

        model.addAttribute("commuteDtoList", commuteDtoList);


        return "commute/workDetail";
    }

    @GetMapping("/workIn/{id}")
    public String workIn(@PathVariable("id") Long id) {

        Long memberId = commuteService.workIn(id);

        return "redirect:/commute/workDetail/" + memberId;
    }

    @GetMapping("/workOut/{id}")
    public String workOut(@PathVariable("id") Long id) {

        Long memberId = commuteService.workOut(id);
        salaryService.overWork(id);

        return "redirect:/commute/workDetail/" + memberId;
    }

    @GetMapping("/vacation")
    public String vacation(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                           Model model,
                           @RequestParam(name = "subject", required = false) String subject,
                           @RequestParam(name = "search", required = false) String search) {

        Page<VacationDto> vacationDtoPage = vacationService.vacationList(pageable, subject, search);

        int totalPage = vacationDtoPage.getTotalPages();//전체page
        int newPage = vacationDtoPage.getNumber();//현재page

        int blockNum = 3; //브라우저에 보이는 페이지 갯수

        int startPage = (int) ((Math.floor(newPage / blockNum) * blockNum) + 1 <= totalPage
            ? (Math.floor(newPage / blockNum) * blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum) - 1 < totalPage ? (startPage + blockNum) - 1 : totalPage;


        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("vacationDtoPage", vacationDtoPage);

        return "commute/vacation";
    }

    @GetMapping("/vacationCreate")
    public String vacationCreate(VacationDto vacationDto, Model model){

        List<MemberDto> memberDtoList = memberService.memberList();

        model.addAttribute("vacationDto", vacationDto);
        model.addAttribute("memberDtoList", memberDtoList);

        return "commute/vacationCreate";
    }

    @PostMapping("/vacationCreate")
    public String vacationCreateOk(VacationDto vacationDto){

        vacationService.vacationCreate(vacationDto);

        return "redirect:/commute/vacation";
    }

    @GetMapping("/myVacation/{id}")
    public String myVacation(@PathVariable("id")Long id, Model model){

        List<VacationDto> vacationDtoList = vacationService.myVacation(id);

        model.addAttribute("vacationDtoList", vacationDtoList);
        
        return "commute/myVacation";
    }
}
