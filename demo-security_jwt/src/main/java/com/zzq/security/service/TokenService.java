package com.zzq.security.service;

import com.zzq.security.bean.TokenImpl;

import java.util.Map;

public interface TokenService {

    TokenImpl parseToken(String token);


    String generateToken(Map<String,Object> map);
}
