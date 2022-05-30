package com.zzq.email.ctrl;


import com.zzq.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/email/{emailAddress}")
    public String sendEmail(@PathVariable String emailAddress) {
        emailService.sendEmail(emailAddress);
        return "success";
    }
}
