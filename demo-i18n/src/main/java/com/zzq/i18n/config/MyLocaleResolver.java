package com.zzq.i18n.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


@Configuration
public class MyLocaleResolver implements LocaleResolver {

    @Autowired
    private HttpServletRequest request;

    public Locale getLocal() {
        return resolveLocale(request);
    }


    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String language = httpServletRequest.getParameter("lang");
        Locale locale = Locale.getDefault();
        if (!StringUtils.isEmpty(language)){
            String[] s = language.split("-");
            locale = new Locale(s[0], s[1]);
        }
        return locale;
    }


    @Override
    public void setLocale(@NonNull HttpServletRequest request, @Nullable HttpServletResponse httpServletResponse, @Nullable Locale locale) {

    }
}
