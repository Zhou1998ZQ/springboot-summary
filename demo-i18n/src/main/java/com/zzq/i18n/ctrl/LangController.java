package com.zzq.i18n.ctrl;

import com.zzq.i18n.util.I18nUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class LangController {

    @GetMapping("/i18n")
    public String i18n(HttpServletRequest request) {
        String message1 = I18nUtil.getMessage("A00001", request.getHeader("lang"));
        String message2 = I18nUtil.getMessage("A00002", request.getHeader("lang"));
        return message1 + message2;
    }

}
