package com.zzq.email.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = MailAccountProperties.PREFIX)
public class MailAccountProperties {
    public static final String PREFIX = "mail.account";

    private String host;
    private Integer port;
    private Boolean auth;
    private String from;
    private String user;
    private String pass;

}
