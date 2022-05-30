package com.zzq.justauth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = GithubProperties.PREFIX)
public class GithubProperties {
    public static final String PREFIX = "auth.github";



    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
