package com.icbc.distributed.interest.schedule;

import com.icbc.distributed.interest.service.InterestComputer;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Quartz的相关配置，注册JobDetail和Trigger
 * 注意JobDetail和Trigger是org.quartz包下的，不是spring包下的，不要导入错误
 */
@Configuration
public class QuarzeConfig {

    @Bean
    public JobDetail jobDetail() {
        JobDetail jobDetail = JobBuilder.newJob(InterestComputer.class)
                .withIdentity("job1", "group1")
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger trigger() {
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("job1", "group1")
                .startNow()
                // 每天0点执行
                .withSchedule(CronScheduleBuilder.cronSchedule("0 1 0 * * ?"))
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 16 * * ?"))
                .build();
        return trigger;
    }
}