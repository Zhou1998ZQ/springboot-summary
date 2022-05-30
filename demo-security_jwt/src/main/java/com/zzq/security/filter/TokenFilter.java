package com.zzq.security.filter;


import com.zzq.security.config.CS;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest)){
           filterChain.doFilter(servletRequest,servletResponse);
           return;
        }

        String token = ((HttpServletRequest) servletRequest).getHeader(CS.TOKEN_HEADER);
        if (token.isBlank()){
            token = servletRequest.getParameter(CS.TOKEN_FIELD);
        }



    }
}
