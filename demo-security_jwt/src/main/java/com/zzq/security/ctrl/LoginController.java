package com.zzq.security.ctrl;

import com.zzq.security.bean.TokenImpl;
import com.zzq.security.dto.LoginRequest;
import com.zzq.security.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody @Validated LoginRequest loginRequest) {


        return loginService.login(loginRequest.getUsername(), loginRequest.getPassword());

    }

    @PreAuthorize("hasRole('ROLE_ADMIN') && hasAuthority('LOL')")
    @GetMapping("/test")
    public String test(){
        TokenImpl authentication = (TokenImpl) SecurityContextHolder.getContext().getAuthentication();
        Long userId = authentication.userId();
        String userName = authentication.userName();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            System.out.println(authority);
        }
        System.out.println(userName);
        System.out.println(userId);

        return userName;
    }
}
