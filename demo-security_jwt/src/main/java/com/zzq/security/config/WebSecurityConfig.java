package com.zzq.security.config;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.map.MapUtil;
import com.zzq.security.annotation.Anonymous;
import com.zzq.security.filter.TokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenFilter tokenFilter;
    private final RequestMappingHandlerMapping requestMapping;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        Map<RequestMethod, Set<String>> anonymousUrls = getAnonymousUrls();

        http
                .csrf().disable()
                .cors().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler(new AccessDeniedHandlerImpl())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/swagger/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS).denyAll()
                .antMatchers(HttpMethod.TRACE).denyAll()
                .antMatchers(HttpMethod.GET, anonymousUrls.get(RequestMethod.GET).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.POST, anonymousUrls.get(RequestMethod.POST).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.DELETE, anonymousUrls.get(RequestMethod.DELETE).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.PUT, anonymousUrls.get(RequestMethod.PUT).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.PATCH, anonymousUrls.get(RequestMethod.PATCH).toArray(new String[0])).permitAll()
                .antMatchers(anonymousUrls.get(RequestMethod.OPTIONS).toArray(new String[0])).permitAll()
                .anyRequest().authenticated()
        ;

        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

    }


    private Map<RequestMethod, Set<String>> getAnonymousUrls() {
        // init
        Map<RequestMethod, Set<String>> urls = MapUtil.of(
                Pair.of(RequestMethod.GET, new HashSet<>()),
                Pair.of(RequestMethod.POST, new HashSet<>()),
                Pair.of(RequestMethod.DELETE, new HashSet<>()),
                Pair.of(RequestMethod.PUT, new HashSet<>()),
                Pair.of(RequestMethod.PATCH, new HashSet<>()),
                Pair.of(RequestMethod.OPTIONS, new HashSet<>())
        );

        // get ctrl method
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo mappingInfo = entry.getKey();

            // check has annotation
            boolean hasAnonymousAnnotation = Objects.nonNull(entry.getValue().getMethodAnnotation(Anonymous.class));

            if (!hasAnonymousAnnotation) {
                Method targetMethod = Objects.requireNonNull(entry.getValue().getMethod());
                Class<?> targetClass = Objects.requireNonNull(targetMethod.getDeclaringClass());

                hasAnonymousAnnotation = Objects.nonNull(targetClass.getAnnotation(Anonymous.class));
            }

            if (!hasAnonymousAnnotation) {
                continue;
            }

            // get method
            Set<RequestMethod> methods = mappingInfo.getMethodsCondition().getMethods()
                    .stream()
                    .filter(requestMethod -> requestMethod != RequestMethod.OPTIONS)
                    .collect(Collectors.toSet());

            if (methods.size() == 0) {
                methods = Set.of(RequestMethod.OPTIONS);
            }

            // get url
            Set<String> patterns = Objects.nonNull(mappingInfo.getPathPatternsCondition()) ?
                    mappingInfo.getPathPatternsCondition().getPatterns()
                            .stream()
                            .map(PathPattern::getPatternString)
                            .collect(Collectors.toSet()) : Set.of();

            for (RequestMethod method : methods) {
                Set<String> target = urls.get(method);
                if (Objects.nonNull(target))
                    target.addAll(patterns);
            }
        }

        return urls;
    }

}
