package com.zzq.quartz;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Scheduled : easy to schedule task
 * Quartz : distributed task scheduler
 */

@EnableScheduling
@SpringBootApplication
public class QuartzAppApplication {
    public static void main(String[] args) {

        SpringApplication.run(QuartzAppApplication.class, args);

    }
}
