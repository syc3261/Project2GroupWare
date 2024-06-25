package org.spring.groupAir.sign.controller;

import lombok.RequiredArgsConstructor;
import org.spring.groupAir.config.MyUserDetailsImpl;
import org.spring.groupAir.department.dto.DepartmentDto;
import org.spring.groupAir.department.dto.TopDepartmentDto;
import org.spring.groupAir.department.service.DepartmentService;
import org.spring.groupAir.department.service.TopDepartmentService;
import org.spring.groupAir.member.dto.MemberDto;
import org.spring.groupAir.member.entity.MemberEntity;
import org.spring.groupAir.member.entity.PositionEntity;
import org.spring.groupAir.member.service.MemberService;
import org.spring.groupAir.member.service.memberServiceInterface.MemberServiceInterface;
import org.spring.groupAir.sign.dto.SignDto;
import org.spring.groupAir.sign.service.SignService;
import org.spring.groupAir.sign.service.serviceInterface.SignServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignController {

    private final SignService signService;
    private final SignServiceInterface signServiceInterface;
    private final MemberService memberService;
    private final MemberServiceInterface memberServiceInterface;

    //부서
    private final TopDepartmentService topDepartmentService;
    private final DepartmentService departmentService;

    @GetMapping({"", "/index"})
    public String index(@AuthenticationPrincipal MyUserDetailsImpl myUserDetails, Model model) {

        String position = memberService.findPosition(myUserDetails.getName());

        model.addAttribute("myUserDetails", myUserDetails);
        model.addAttribute("position", position);

        return "sign/index";
    }
//기존write
//    @GetMapping("/write")
//    public String write(Model model) {
//        model.addAttribute("signDto", new SignDto());
//        return "sign/write";
//    }


    @GetMapping("/write")
    public String write(@AuthenticationPrincipal MyUserDetailsImpl myUserDetails,
                        TopDepartmentDto topDepartmentDto,
                        DepartmentDto departmentDto,
                        Model model) {

        String name = myUserDetails.getName();

        model.addAttribute("signDto", new SignDto());

       List<MemberDto> memberDtoList = memberService.findBujang();



        List<TopDepartmentDto> list = topDepartmentService.List(topDepartmentDto);

        //List<DepartmentDto> deptList = departmentService.getSubDepartments(departmentDto.getId());


        model.addAttribute("members", memberDtoList);
        model.addAttribute("name", name);
        model.addAttribute("list", list);

       // model.addAttribute("deptList", deptList);


        return "sign/write";
    }

    @PostMapping("/write")
    public String writeOk(@Valid SignDto signDto, BindingResult bindingResult,@AuthenticationPrincipal MyUserDetailsImpl myUserDetails) throws IOException {



        signDto.setMemberEntity(MemberEntity.builder().id(myUserDetails.getMemberEntity().getId()).build());

        if (bindingResult.hasErrors()) {
            return "sign/write";
        }
//        signService.write(signDto);

        Long id = signService.insertSign(signDto);


        return "redirect:/sign/approveSignList";
    }


//    @GetMapping("/approveSignMemberList")
////    @ResponseBody
//    public String approveSignList( @AuthenticationPrincipal MyUserDetailsImpl myUserDetails,
//                                   @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
//                                   @RequestParam(name = "subject", required = false) String subject,
//                                   @RequestParam(name = "search", required = false) String search,
//                                  Model model) {
//
//        System.out.println(myUserDetails.getMemberEntity().getId()+" <<id");
//        // 로그인 한 사람의 결제 정보 만
////        List<SignDto> signDtoList=signService.signListById(myUserDetails.getMemberEntity().getId());
//        Page<SignDto> signDtoList=signService.signListById(pageable, subject, search,myUserDetails.getMemberEntity().getId() );
//
//        int totalPages = signDtoList.getTotalPages();
//        int newPage = signDtoList.getNumber();
//
//        int blockNum = 10;
//        int startPage = (int) (
//                (Math.floor(newPage / blockNum) * blockNum) + 1 <=
//                        totalPages ? (Math.floor(newPage / blockNum) * blockNum) + 1 : totalPages);
//        int endPage = (startPage + blockNum) - 1 < totalPages ? (startPage + blockNum) - 1 : totalPages;
//
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("newPage", newPage);
//        model.addAttribute("endPage", endPage);
//        model.addAttribute("signDtoPage", signDtoList);
//
//        return "sign/approveSignList";
//
//    }



    @GetMapping("/signList")
    public String signList(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                           @RequestParam(name = "subject", required = false) String subject,
                           @RequestParam(name = "search", required = false) String search,
//                           @RequestParam(name = "lastApprover", required = false) String lastApprover,
                           @AuthenticationPrincipal MyUserDetailsImpl myUserDetails,
                           TopDepartmentService topDepartmentService,
                           Model model) {

        String name = myUserDetails.getName();
//        String name = "유부장";



        Page<SignDto> signDtoPage = signService.apvList(pageable, subject, search, name);

        int totalPages = signDtoPage.getTotalPages();
        int newPage = signDtoPage.getNumber();

        int blockNum = 10;
        int startPage = (int) (
                (Math.floor(newPage / blockNum) * blockNum) + 1 <=
                        totalPages ? (Math.floor(newPage / blockNum) * blockNum) + 1 : totalPages);
        int endPage = (startPage + blockNum) - 1 < totalPages ? (startPage + blockNum) - 1 : totalPages;

        model.addAttribute("startPage", startPage);
        model.addAttribute("newPage", newPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("signDtoPage", signDtoPage);




        return "sign/signList";


    }

    @GetMapping("/approveSignList")
    public String approveSignList(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                  @RequestParam(name = "subject", required = false) String subject,
                                  @RequestParam(name = "search", required = false) String search,
//                           @RequestParam(name = "lastApprover", required = false) String lastApprover,
                                  @AuthenticationPrincipal MyUserDetailsImpl myUserDetails,
                                  Model model) {

    String name = myUserDetails.getMemberEntity().getName();
//        String name = "유부장";
        Page<SignDto> signDtoPage = signService.myApvList(pageable, subject, search, name);
//        Page<SignDto> signDtoPage = signService.myApvList(pageable, subject, search, null);
        int totalPages = signDtoPage.getTotalPages();
        int newPage = signDtoPage.getNumber();

        int blockNum = 10;
        int startPage = (int) (
                (Math.floor(newPage / blockNum) * blockNum) + 1 <=
                        totalPages ? (Math.floor(newPage / blockNum) * blockNum) + 1 : totalPages);
        int endPage = (startPage + blockNum) - 1 < totalPages ? (startPage + blockNum) - 1 : totalPages;

        model.addAttribute("startPage", startPage);
        model.addAttribute("newPage", newPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("signDtoPage", signDtoPage);


        return "sign/approveSignList";


    }


//기존디테일

//    @GetMapping("/detail/{id}")
//    public String detail(@PathVariable("id") Long id, Model model) {
//        SignDto sign = signService.signOne(id);
//        if (sign != null) {
//            model.addAttribute("sign", sign);
//        }
//        return "sign/detail";
//    }


    @GetMapping("/detail/{id}")
    public String detail(@AuthenticationPrincipal MyUserDetailsImpl myUserDetails,
                         @PathVariable("id") Long id,
                         Model model) {
        SignDto sign = signService.signOne(id);
        String selectedUserName = sign.getLastApprover();
        String position = memberService.findPosition(myUserDetails.getName());
        model.addAttribute("sign", sign);
        model.addAttribute("selectedUserName", selectedUserName);
        model.addAttribute("position", position);
        return "sign/detail";
    }

//    @GetMapping("/submitDetail/{id}")
//    public String submitDetail(@PathVariable("id") Long id , Model model){
//        SignDto approveSign= signService.approveSignOne(id);
//        String selectedUserName = approveSign.getLastApprover(); // 선택된 사용자의 이름 가져오기
//        model.addAttribute("approveSign" , approveSign);
//        model.addAttribute("selectedUserName", selectedUserName);
//
//        return "sign/submitDetail";
//
//
//
//    }


    //승인자만보이게
//    @GetMapping("/detail/{id}")
//    public String detail(@PathVariable("id") Long id, Model model) {
//        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        // 현재 사용자가 승인자인지를 확인합니다.
//        boolean isApprover = checkUserRole(currentUserName, "APPROVER");
//
//        // 승인자인 경우에만 상세 페이지로 이동합니다.
//        if (isApprover) {
//            SignDto sign = signService.signOne(id);
//            if (sign != null) {
//                model.addAttribute("sign", sign);
//                return "sign/detail";
//            }
//        }

    // 승인자가 아닌 경우에는 접근 거부 페이지를 반환합니다.
    //      return "sign/error";
    //  }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        signService.deleteSign(id);
        return "redirect:/sign";
    }

//    @PostMapping("/update")
//    public String update(@ModelAttribute SignDto signDto) {
//        signService.update(signDto);
//        return "redirect:/sign/signOk/" + signDto.getId();
//    }

    private String getCurrentUserName() {
        // 현재 로그인한 사용자의 이름을 반환하는 로직
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/signOk")
    public String signOk(@AuthenticationPrincipal MyUserDetailsImpl myUserDetails,

                         Model model) {

        String name = myUserDetails.getName();
        List<SignDto> signOkList = signService.getAllSignOk(name);
        model.addAttribute("signOkList", signOkList);
        return "sign/signOk";

    }


    @PostMapping("/signOk")
    public String signOk(@Valid SignDto signDto, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {


            return "sign/signList";
        }

//        System.out.println(signDto.getRejectReason()+" <<< 승인");

        signService.update(signDto);

        List<SignDto> signOkList = signService.signSubContnetList(signDto.getSubContent());

        model.addAttribute("signOkList", signOkList);

        System.out.println(signOkList + " signOkList");

        // 페이지 이동
        return "redirect:/sign/signList";
        // return "sign/signOk";

    }

    @GetMapping("/signNo")
    public String signNO(@AuthenticationPrincipal MyUserDetailsImpl myUserDetails,
                         Model model) {
        String name = myUserDetails.getName();
        List<SignDto> signNoList = signService.getAllSignNo(name);
        model.addAttribute("signNoList", signNoList);
        return "sign/signNO";

    }


    @PostMapping("/signNO")
    public String signNO(@Valid SignDto signDto, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {


            return "sign/signList";
        }

//        System.out.println(signDto.getRejectReason()+" <<< 승인");

        signService.update(signDto);

        List<SignDto> signNoList = signService.signSubContnetList(signDto.getSubContent());

        model.addAttribute("signNoList", signNoList);

        System.out.println(signNoList + " signNoList");

        // 페이지 이동
        return "redirect:/sign/signList";
        // return "sign/signOk";

    }
}















