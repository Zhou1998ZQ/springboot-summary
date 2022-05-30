package com.zzq.quartz.scheduled;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @see <a href="http://cron.qqe2.com/">...</a>
 */
@Slf4j
@Component
public class AnnotationScheduled {

    // every 2 minutes

    @Scheduled(cron = "0 0/2 * * * ?")
    public void printTime() {
        log.info(StrUtil.format("AnnotationScheduled , current time : {}", LocalDateTimeUtil.now()));
    }
}
