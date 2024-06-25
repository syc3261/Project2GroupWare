package org.spring.groupAir.member.controller;

import lombok.RequiredArgsConstructor;
import org.spring.groupAir.commute.service.CommuteService;
import org.spring.groupAir.config.MyUserDetailsImpl;
import org.spring.groupAir.department.dto.TopDepartmentDto;
import org.spring.groupAir.department.service.DepartmentService;
import org.spring.groupAir.department.service.TopDepartmentService;
import org.spring.groupAir.member.dto.MemberDto;
import org.spring.groupAir.member.dto.PositionDto;
import org.spring.groupAir.member.entity.MemberEntity;
import org.spring.groupAir.member.entity.PositionEntity;
import org.spring.groupAir.member.service.MemberService;
import org.spring.groupAir.salary.service.SalaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final CommuteService commuteService;
    private final SalaryService salaryService;

    // 부서
    private final TopDepartmentService topDepartmentService;
    private final DepartmentService departmentService;

    @GetMapping("/memberJoin")
    public String memberJoin(MemberDto memberDto, TopDepartmentDto topDepartmentDto,
                             Model model) {

        List<TopDepartmentDto> list = topDepartmentService.List(topDepartmentDto);

        model.addAttribute("list", list);
        model.addAttribute("memberDto", memberDto);



        return "member/memberJoin";
    }

    @PostMapping("/memberJoin")
    public String memberJoinOk(@Valid MemberDto memberDto,
                               BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "member/memberJoin";
        } else {
            Long memberId = memberService.memberJoin(memberDto);
            commuteService.createCommute(memberId);
            salaryService.createSalary(memberId);
        }

        return "redirect:/member/memberList";
    }


    @GetMapping("/memberList")
    public String work(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       Model model,
                       @RequestParam(name = "subject", required = false) String subject,
                       @RequestParam(name = "search", required = false) String search,
                       @AuthenticationPrincipal MyUserDetailsImpl myUserDetails) {
        Page<MemberDto> memberList = memberService.memberList(pageable, subject, search);

        int totalPage = memberList.getTotalPages();//전체page
        int newPage = memberList.getNumber();//현재page
        Long totalElements = memberList.getTotalElements();//전체 레코드 갯수
        int size = memberList.getSize();//페이지당 보이는 갯수

        int blockNum = 3; //브라우저에 보이는 페이지 갯수

        int startPage = (int) ((Math.floor(newPage / blockNum) * blockNum) + 1 <= totalPage
                ? (Math.floor(newPage / blockNum) * blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum) - 1 < totalPage ? (startPage + blockNum) - 1 : totalPage;

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("memberList", memberList);

        model.addAttribute("myUserDetails", myUserDetails);

        return "member/memberList";
    }

    @GetMapping("/memberDetail/{id}")
    public ResponseEntity<MemberDto> memberDetail(@PathVariable("id") Long id, Model model){
        MemberDto member= memberService.memberDetail(id);

        if(member!=null) model.addAttribute("key","member");
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PostMapping("/memberUpdate2")
    public ResponseEntity<Long> memberUpdate2(@RequestBody MemberDto memberDto) throws IOException {
        MemberEntity memberEntity = memberService.memberUpdate2(memberDto);
        return new ResponseEntity<>(memberEntity.getId(), HttpStatus.OK);
    }

    @PostMapping("/memberDelete2")
    public ResponseEntity<Long> memberDelete2(@RequestBody MemberDto memberDto){
        memberService.memberDelete(memberDto.getId());
        return new ResponseEntity<>(memberDto.getId(), HttpStatus.OK);
    }


    @GetMapping("/memberUpdate/{id}")
    public String memberDetail1(@PathVariable("id") Long id,
                               @AuthenticationPrincipal MyUserDetailsImpl myUserDetails,
                               Model model,TopDepartmentDto topDepartmentDto) {
        List<TopDepartmentDto> list = topDepartmentService.List(topDepartmentDto);

        model.addAttribute("list", list);
        MemberDto memberDto = memberService.memberDetail(id);

        model.addAttribute("memberDto", memberDto);


        return "member/memberUpdate";
    }
    @PostMapping("/memberUpdate")
    public String memberUpdate(MemberDto memberDto) throws IOException {

        memberService.memberUpdate(memberDto);


        return "redirect:/member/memberUpdate/" + memberDto.getId();
    }

    @GetMapping("/memberDelete/{id}")
    @ResponseBody
    public String memberDelete(@PathVariable("id") Long id, Model model,MemberDto memberDto) {

        memberService.memberDelete(id);

        String html = "<script>" +
            "alert('회원 탈퇴 성공');" +
            "location.href='/member/memberList'" +
            "</script>";

        return html;
    }

    @GetMapping("/findEmail")
    public String findEmailForm() {
        return "member/findEmail";
    }

    @PostMapping("/find-email")
    public ResponseEntity<Map<String, Object>> findEmail(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String phone = request.get("phone");
        Map<String, Object> response = new HashMap<>();

        try {
            String email = memberService.findUserEmailByNameAndPhone(name, phone);
            if (email != null) {
                response.put("success", true);
                response.put("email", email);
            } else {
                response.put("success", false);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "오류가 발생했습니다: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/findPassword")
    public String findPasswordForm() {



        return "member/findPassword";
    }

    @PostMapping("/find-password")
    public ResponseEntity<Map<String, Object>> findPassword(@RequestBody Map<String, String> request) {
        String userEmail = request.get("userEmail");
        String name = request.get("name");
        Map<String, Object> response = new HashMap<>();

        try {
            String email = memberService.findUserPwByUserEmailAndName(userEmail, name);
            if (email != null) {
                response.put("success", true);
                response.put("email", email);
            } else {
                response.put("success", false);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "오류가 발생했습니다: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }


    @GetMapping("/changePw/{userEmail}")
    public String changePw(@PathVariable("userEmail") String userEmail, Model model) {

        MemberDto memberDto = memberService.findMyId(userEmail);

        model.addAttribute("memberDto", memberDto);

        return "member/changePw";
    }


    @PostMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody Map<String, String> request, Model model) {

        String userEmail = request.get("userEmail");
        String name = request.get("name");
        String newPassword = request.get("newPassword");

        System.out.println("userEmail : " + userEmail);
        System.out.println("name : " + name);
        System.out.println("newPassword : " + newPassword);

        Map<String, Object> response = new HashMap<>();

        try {
           ;
//            boolean success = memberService.changePasswordByUserEmail( memberService.findByUserEmail(userEmail), newPassword);
            boolean success = memberService.changePasswordByEmailAndName(userEmail, name, newPassword);
            if (success) {
                response.put("success", true);
            } else {
                response.put("success", false);
                response.put("message", "비밀번호 변경에 실패했습니다.");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "오류가 발생했습니다: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }
}


