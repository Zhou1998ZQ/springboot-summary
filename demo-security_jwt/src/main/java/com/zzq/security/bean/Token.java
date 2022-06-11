package com.zzq.security.bean;

import java.util.Collection;

public interface Token {
    Long userId();
    String userName();
    Collection<String> authorities();

}
