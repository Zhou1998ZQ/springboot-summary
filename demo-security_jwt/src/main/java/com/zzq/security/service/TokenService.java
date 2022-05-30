package com.zzq.security.service;

import java.util.Map;

public interface TokenService {

    Object parseToken(String token);


    String generateToken(Map<String,Object> map);
}
