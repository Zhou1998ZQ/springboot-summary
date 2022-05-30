package com.zzq.quartz.job;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class FirstJob extends QuartzJobBean {


    @Override
    @Transactional(rollbackFor = Exception.class)
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        //business logic code here
        log.info(StrUtil.format("FirstJob executeInternal , current time : {}", LocalDateTimeUtil.now()));
    }


}
