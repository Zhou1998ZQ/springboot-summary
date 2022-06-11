package com.zzq.security.bean;

import lombok.Builder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

@Builder
public class TokenImpl implements Authentication ,Token{

    private Long userId;
    private String username;
    private Collection<String> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Long userId() {
        return userId;
    }

    @Override
    public String userName() {
        return username;
    }

    @Override
    public Collection<String> authorities() {
        return authorities;
    }
}
