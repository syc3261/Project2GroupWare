package org.spring.groupAir.department.controller;

import lombok.RequiredArgsConstructor;
import org.spring.groupAir.config.MyUserDetailsImpl;
import org.spring.groupAir.department.dto.DepartmentDto;
import org.spring.groupAir.department.dto.TopDepartmentDto;
import org.spring.groupAir.department.service.DepartmentService;
import org.spring.groupAir.department.service.TopDepartmentService;
import org.spring.groupAir.member.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/department")
@SessionAttributes("list")
public class DepartmentController {

    private final TopDepartmentService topDepartmentService;
    private final DepartmentService departmentService;

    @GetMapping({"/", "/index"})
    public String department() {

        return "department/top/deList";
    }

    @GetMapping("/write")
    public String write(@ModelAttribute("list") List<TopDepartmentDto> list, DepartmentDto departmentDto, Model model) {

        model.addAttribute("departmentDto", departmentDto);

        return "department/write";
    }

    @PostMapping("/deWrite")
    public String deWrite(@Valid DepartmentDto departmentDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "department/write";
        } else {
            departmentService.write(departmentDto);
        }

        return "redirect:/department/top/deList";
    }

    @GetMapping("/detail/{id}")
    public String deDetail(@PathVariable("id") Long id, Model model
            , @ModelAttribute("list") List<TopDepartmentDto> list) {

        DepartmentDto department = departmentService.detail(id);
        model.addAttribute("activeId", id); // 현재 활성화된 ID를 모델에 추가

        model.addAttribute("department", department);

        return "department/detail";
    }

    @PostMapping("/update")
    public String update(DepartmentDto departmentDto) {

        departmentService.update(departmentDto);

        return "redirect:/department/top/deList";
    }

    @GetMapping("/delete/{id}")
    public String deCheckDelete(@PathVariable("id") Long id) {

        departmentService.delete(id);

        return "redirect:/department/top/deList";
    }

    @GetMapping("top/detail/{id}")
    public String topDeDetail(@PathVariable("id") Long id, Model model
            , @ModelAttribute("list") List<TopDepartmentDto> list) {

        TopDepartmentDto topDepartment = topDepartmentService.detail(id);

        model.addAttribute("topDepartment", topDepartment);

        return "department/top/detail";
    }

    @GetMapping("/top/write")
    public String tWrite(@ModelAttribute("list") List<TopDepartmentDto> list, TopDepartmentDto topDepartmentDto, Model model) {

        model.addAttribute("topDepartmentDto", topDepartmentDto);

        return "department/top/write";
    }

    @PostMapping("/top/deTopWrite")
    public String deWrite(@Valid TopDepartmentDto topDepartmentDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "department/top/write";
        } else {
            topDepartmentService.write(topDepartmentDto);
        }


        return "redirect:/department/top/deList";
    }

    @PostMapping("top/update")
    public String update(TopDepartmentDto topDepartmentDto) {

        topDepartmentService.update(topDepartmentDto);


        return "redirect:/department/top/deList";
    }

    @GetMapping("/top/delete/{id}")
    public String topDeDelete(@PathVariable("id") Long id) {

        topDepartmentService.detele(id);


        return "redirect:/department/top/deList";
    }


    @GetMapping("/top/deList")
    public String deList(TopDepartmentDto topDepartmentDto,
                         HttpSession session, Model model,
                         @AuthenticationPrincipal MyUserDetailsImpl myUserDetails, MemberEntity memberEntity) {

        if (myUserDetails.getMemberEntity().getRole().toString() == "ADMIN") {

            List<TopDepartmentDto> list = topDepartmentService.List(topDepartmentDto);
            model.addAttribute("list", list);

        } else if (myUserDetails.getMemberEntity().getRole().toString() == "MANAGER") {

            Long id = myUserDetails.getMemberEntity().getId();

            List<TopDepartmentDto> list = topDepartmentService.ListManager(topDepartmentDto, id);
            model.addAttribute("list", list);

        } else {
            List<TopDepartmentDto> list = List.of();
            model.addAttribute("list", list);
        }

        // 선택한 부서 background 주기 위해 사용
        model.addAttribute("activeId", null);

        return "department/top/deList";
    }


    // 회원가입 부서가져오기
    @GetMapping("/list")
    @ResponseBody
    public List<TopDepartmentDto> deList(TopDepartmentDto topDepartmentDto) {
        return topDepartmentService.List(topDepartmentDto);
    }

    @GetMapping("/subDepartments")
    @ResponseBody
    public List<DepartmentDto> getSubDepartments(@RequestParam("topDepartmentId") Long topDepartmentId) {
        // 선택된 상위 부서에 해당하는 하위 부서 목록을 가져옴
        return departmentService.getSubDepartments(topDepartmentId);
    }

    // 날씨 테스트
    @GetMapping("/weather")
    public String weather() {

        return "department/weather";
    }


}
