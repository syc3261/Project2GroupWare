package org.spring.groupAir.chatbot.controller;

import org.spring.groupAir.chatbot.dto.MessageDto;
import org.spring.groupAir.chatbot.service.KomoranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChatBotController {

    @Autowired
    KomoranService komoranService;

    @PostMapping("/botController")
    public String message(String message, Model model) throws Exception {

        model.addAttribute("msg", komoranService.nlpAnalyze(message));

        return "common/chatbot/bot-message";
    }

}
