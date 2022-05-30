package com.zzq.email.config;

import cn.hutool.extra.mail.MailAccount;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(MailAccountProperties.class)
public class MailAccountConfig {

    @Bean
    public MailAccount mailAccount(MailAccountProperties mailAccountProperties) {
        MailAccount mailAccount = new MailAccount();
        mailAccount.setHost(mailAccountProperties.getHost());
        mailAccount.setPort(mailAccountProperties.getPort());
        mailAccount.setAuth(mailAccountProperties.getAuth());
        mailAccount.setFrom(mailAccountProperties.getFrom());
        mailAccount.setUser(mailAccountProperties.getUser());
        mailAccount.setPass(mailAccountProperties.getPass());
        return mailAccount;
    }

}
