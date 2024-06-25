package org.spring.groupAir.chat;

import lombok.RequiredArgsConstructor;
import org.spring.groupAir.member.dto.MemberDto;
import org.spring.groupAir.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final MemberService memberService;

    @GetMapping({"/{id}", "/chat/{id}"})
    public String chat(@PathVariable("id") Long id, Model model) {

        MemberDto member = memberService.memberDetail(id);

        model.addAttribute("member", member);

        return "chat/chatRoom";
    }

//    @GetMapping({"/", "/chat"})
//    public String index() {
//        return "chat/chatRoom";
//    }


}
