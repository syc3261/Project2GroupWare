package org.spring.groupAir.salary.controller;

import lombok.RequiredArgsConstructor;
import org.spring.groupAir.commute.service.VacationService;
import org.spring.groupAir.salary.dto.SalaryDto;
import org.spring.groupAir.salary.service.SalaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/salary")
public class SalaryController {

    private final SalaryService salaryService;

    @GetMapping({"/", "/index", ""})
    public String salaryIndex(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                              Model model) {

        salaryService.updateSalaryDate();

        Page<SalaryDto> salaryDtoPage = salaryService.memberSalary(pageable);

        int totalPage = salaryDtoPage.getTotalPages();//전체page
        int newPage = salaryDtoPage.getNumber();//현재page
        int blockNum = 3; //브라우저에 보이는 페이지 갯수

        int startPage = (int) ((Math.floor(newPage / blockNum) * blockNum) + 1 <= totalPage
            ? (Math.floor(newPage / blockNum) * blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum) - 1 < totalPage ? (startPage + blockNum) - 1 : totalPage;

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("salaryDtoPage", salaryDtoPage);

        return "salary/index";
    }

    @GetMapping("/update/{id}")
    public String updateSalary(@PathVariable("id")Long id, Model model){

        SalaryDto salaryDto = salaryService.updateSalary(id);

        model.addAttribute("salaryDto", salaryDto);

        return "salary/update";
    }

    @PostMapping("/update")
    public String update(SalaryDto salaryDto){

         salaryService.update(salaryDto);

         return "redirect:/salary/index";
    }

    @GetMapping("/mySalary/{id}")
    public String mySalary(@PathVariable("id")Long id, Model model){

        List<SalaryDto> salaryDtoList = salaryService.mySalary(id);

        model.addAttribute("salaryDtoList", salaryDtoList);

        return "salary/mySalary";
    }
}
