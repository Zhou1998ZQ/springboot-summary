package com.zzq.quartz.config;

import com.zzq.quartz.job.FirstJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    public static final String JOB_DETAIL_IDENTITY = "DateTimeJob";

    @Bean
    public JobDetail printTimeJobDetail() {
        return JobBuilder.newJob(FirstJob.class)
                .withIdentity(JOB_DETAIL_IDENTITY)
                .storeDurably()
                .build();
    }


    @Bean
    public Trigger printTimeJobTrigger() {
        //SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.repeatMinutelyForever();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/1 * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(printTimeJobDetail())
                .withSchedule(cronScheduleBuilder)
                .build();
    }

}
