package com.zzq.justauth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = WechatProperties.PREFIX)
public class WechatProperties {

    public static final String PREFIX = "auth.wechat";

    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
