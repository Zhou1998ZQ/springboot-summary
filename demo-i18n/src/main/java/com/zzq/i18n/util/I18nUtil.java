package com.zzq.i18n.util;

import com.zzq.i18n.config.MyLocaleResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Component
public class I18nUtil {

    @Value("${spring.messages.basename}")
    private String basename;

    private final MyLocaleResolver resolver;

    private static MyLocaleResolver customLocaleResolver;

    private static String path;


    public I18nUtil(MyLocaleResolver resolver) {
        this.resolver = resolver;
    }


    @PostConstruct
    public void init() {
        setBasename(basename);
        setCustomLocaleResolver(resolver);
    }


    public static String getMessage(String code) {
        Locale locale = customLocaleResolver.getLocal();
        return getMessage(code, null, code, locale);
    }


    public static String getMessage(String code, String lang) {
        Locale locale;
        if (StringUtils.isEmpty(lang)) {
            locale = Locale.CANADA;
        } else {
            try {
                var split = lang.split("-");
                locale = new Locale(split[0], split[1]);
            } catch (Exception e) {
                locale = Locale.CANADA;
            }
        }
        return getMessage(code, null, code, locale);
    }



    public static String getStationLetterMessage(String code, String lang) {
        Locale locale = null;
        if (Objects.equals(lang, "zh-CN")){
            locale =  Locale.SIMPLIFIED_CHINESE;
        }
        if (Objects.equals(lang, "en-CA") || Objects.equals(lang, "en-US") ){
            locale =  Locale.US;
        }
        if (Objects.equals(lang, "fr-CA")){
            locale =  Locale.CANADA_FRENCH;
        }


        return getMessage(code, null, code, locale);

    }



    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.toString());
        messageSource.setBasename(path);
        String content;
        try {
            content = messageSource.getMessage(code, args, locale);
        } catch (Exception e) {
            log.error("i18n  error ---> {},{}", e.getMessage(), e);
            content = defaultMessage;
        }
        return content;

    }

    public static void setBasename(String basename) {
        I18nUtil.path = basename;
    }

    public static void setCustomLocaleResolver(MyLocaleResolver resolver) {
        I18nUtil.customLocaleResolver = resolver;
    }

}
