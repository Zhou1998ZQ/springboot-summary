package com.zzq.justauth.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.xkcoding.http.config.HttpConfig;
import com.zzq.justauth.properties.GithubProperties;
import com.zzq.justauth.properties.GoogleProperties;
import com.zzq.justauth.properties.WechatProperties;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthGoogleRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.request.AuthWeChatOpenRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
@RequiredArgsConstructor
public class JustAuthConfig {

    private final GoogleProperties googleProperties;
    private final GithubProperties githubProperties;
    private final WechatProperties wechatProperties;

    @Bean
    @Primary
    Jackson2ObjectMapperBuilderCustomizer jacksonCustomer() {

        SimpleModule module = new SimpleModule();

        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DatePattern.NORM_DATETIME_FORMATTER));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DatePattern.NORM_DATETIME_FORMATTER));
        module.addSerializer(LocalDate.class, new LocalDateSerializer(DatePattern.NORM_DATE_FORMATTER));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DatePattern.NORM_DATE_FORMATTER));
        module.addSerializer(LocalTime.class, new LocalTimeSerializer(DatePattern.NORM_TIME_FORMATTER));
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DatePattern.NORM_TIME_FORMATTER));

        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.modules(module).build();

    }


    @Bean(name = "authGoogleRequest")
    @ConditionalOnBean(GoogleProperties.class)
    public AuthRequest authGoogleRequest() {
        return new AuthGoogleRequest(AuthConfig.builder()
                .clientId(googleProperties.getClientId())
                .clientSecret(googleProperties.getClientSecret())
                .redirectUri(googleProperties.getRedirectUri())
                .httpConfig(HttpConfig.builder()
                        .timeout(15000)
                        .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10080)))
                        .build())
                .build());
    }


    @Bean("authGithubRequest")
    @ConditionalOnBean(GithubProperties.class)
    public AuthRequest authGithubRequest() {
        return new AuthGithubRequest(AuthConfig.builder()
                .clientId(githubProperties.getClientId())
                .clientSecret(githubProperties.getClientSecret())
                .redirectUri(githubProperties.getRedirectUri())
                .build());
    }

    @Bean("authWechatRequest")
    @ConditionalOnBean(WechatProperties.class)
    public AuthRequest authWechatRequest() {
        return new AuthWeChatOpenRequest(AuthConfig.builder()
                .clientId(wechatProperties.getClientId())
                .clientSecret(wechatProperties.getClientSecret())
                .redirectUri(wechatProperties.getRedirectUri())
                .build());
    }


}
