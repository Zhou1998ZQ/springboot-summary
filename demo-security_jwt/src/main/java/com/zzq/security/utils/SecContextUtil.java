package com.zzq.security.utils;

import com.zzq.security.bean.TokenImpl;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class SecContextUtil {

    public Long getUserId() {
        return authentication().userId();
    }

    public String getUsername() {
        return authentication().userName();
    }

    public Collection<String> authorities() {
        return authentication().authorities();
    }

    public TokenImpl authentication() {
        return (TokenImpl) SecurityContextHolder.getContext().getAuthentication();
    }
}
