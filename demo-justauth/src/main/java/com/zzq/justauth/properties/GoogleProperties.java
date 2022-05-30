package com.zzq.justauth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = GoogleProperties.PREFIX)
public class GoogleProperties {
    public static final String PREFIX = "auth.google";

    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
