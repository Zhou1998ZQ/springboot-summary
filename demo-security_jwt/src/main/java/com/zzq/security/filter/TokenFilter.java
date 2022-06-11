package com.zzq.security.filter;


import cn.hutool.core.util.StrUtil;
import com.zzq.security.bean.TokenImpl;
import com.zzq.security.config.CS;
import com.zzq.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Component
public class TokenFilter implements Filter {

    private final TokenService tokenService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest)){
           filterChain.doFilter(servletRequest,servletResponse);
           return;
        }

        String token = ((HttpServletRequest) servletRequest).getHeader(CS.TOKEN_HEADER);
        if (isNull(token)){
            token = servletRequest.getParameter(CS.TOKEN_FIELD);
        }
        // has token
        if (StrUtil.isNotBlank(token)){
            TokenImpl parseToken = tokenService.parseToken(token);

            SecurityContextHolder.getContext().setAuthentication(parseToken);
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
