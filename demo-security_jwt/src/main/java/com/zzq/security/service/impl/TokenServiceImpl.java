package com.zzq.security.service.impl;

import com.zzq.security.config.CS;
import com.zzq.security.service.TokenService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public Object parseToken(String token) {
//        Jwts.parser().parse(token)

        return null;
    }

    @Override
    public String generateToken(Map<String,Object> map) {
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, CS.TOKEN_SECRET)
                // 1 year
                .setExpiration(new Date(System.currentTimeMillis() + (60L * 60 * 24 * 365 * 1000)))
                .setClaims(map)
                .compact();

    }
}
