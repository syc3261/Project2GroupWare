package org.spring.groupAir.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class EmailController {
    private final MailService emailService;


    @ResponseBody
    @PostMapping("/mail")
    public String mailSend(@RequestParam String mail) {
        int number = emailService.sendMail(mail);
        return String.valueOf(number);
    }
}
