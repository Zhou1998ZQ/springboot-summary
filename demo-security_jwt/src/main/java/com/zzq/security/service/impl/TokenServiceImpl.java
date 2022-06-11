package com.zzq.security.service.impl;

import com.zzq.security.bean.TokenImpl;
import com.zzq.security.config.CS;
import com.zzq.security.service.TokenService;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {

    @SuppressWarnings("unchecked")
    @Override
    public TokenImpl parseToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(CS.TOKEN_SECRET)
                .parseClaimsJws(token);
        Claims body = claimsJws.getBody();

        return TokenImpl.builder().authorities((Collection<String>) body.get("authorities"))
                .userId(((Integer) body.get("userId")).longValue())
                .username((String) body.get("username"))
                .build();


    }


    @Override
    public String generateToken(Map<String, Object> map) {
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, CS.TOKEN_SECRET)
                // 1 year
                .setExpiration(new Date(System.currentTimeMillis() + (60L * 60 * 24 * 365 * 1000)))
                .setClaims(map)
                .compact();

    }
}
