package com.hy.wyy.quartz.test;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import java.text.ParseException;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;

/**
 * Created by panrui on 2014/5/30.
 */
public class SimpleTriggerRunner {
    public void task() throws SchedulerException{

    SchedulerFactory schedulerFactory=new StdSchedulerFactory();
    Scheduler scheduler=schedulerFactory.getScheduler();
        long ctime=System.currentTimeMillis();
        JobDetail jobDetail=newJob(SimpleJob.class) .withIdentity("job1", "jobGroup1")
                .build();
//        SimpleTrigger simpleTrigger=TriggerBuilder.newTrigger().withIdentity("simpleTrigger","TriggerGroup1").withSchedule(Simp);
    }

    public void task2() throws SchedulerException {
        SchedulerFactory schedulerFactory =new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        long ctime = System.currentTimeMillis();
        try {
            CronExpression cronExpression=new CronExpression("0/5 * * * * ?");
        TriggerBuilder<CronTrigger> cronTrigger = TriggerBuilder.newTrigger().withIdentity("CronTrigger1","CronGroup1")
                .withSchedule(cronSchedule("0/20 * * * * ?"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
