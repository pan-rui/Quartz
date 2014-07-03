package com.hy.wyy.quartz.test;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


/**
 * Created by panrui on 2014/5/30.
 */
public class SimpleTrigger2 {
    public void run() throws Exception {
        org.slf4j.Logger log= LoggerFactory.getLogger(SimpleTrigger2.class);

        SchedulerFactory sf=new StdSchedulerFactory();
        Scheduler sch=sf.getScheduler();

        Date startTime= DateBuilder.nextGivenSecondDate(null,15);

        JobDetail job= newJob(SimpleJob.class).withIdentity("jo1","g1")
                .build();

        SimpleTrigger trigger=(SimpleTrigger) newTrigger().withIdentity("t01","g1")
             .startAt(startTime).build();

        Date ft=sch.scheduleJob(job,trigger);

        job =newJob(SimpleJob.class).withIdentity("j2","g1").build();

        trigger=(SimpleTrigger)newTrigger().withIdentity("t2","g1").startAt(startTime).build();

        ft=sch.scheduleJob(job,trigger);
        log.info("j2................");
        job = newJob(SimpleJob.class).withIdentity("j3","g1").build();

        trigger=newTrigger().withIdentity("t3","g1 ").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10))
                .build();
        ft=sch.scheduleJob(job,trigger);
        log.info("j3...............");

        trigger=newTrigger().withIdentity("t3","g2")
                .startAt(startTime).withSchedule(simpleSchedule().withIntervalInSeconds(10)
                .withRepeatCount(2)).forJob(job).build();

        ft=sch.scheduleJob(trigger);
        log.info(job.getKey()+"will [also] run at:"+ft+"..............");
//        futureDate(5, DateBuilder.IntervalUnit.MINUTE);
        job = newJob(SimpleJob.class).withIdentity("j8","g1")
                .storeDurably(true).build();
        sch.addJob(job,true);//手动立即执行
        sch.triggerJob(jobKey("j8","g1"));
        Thread.sleep(3000L);

        trigger = newTrigger().withIdentity("t7","g1").startAt(startTime)
                .withSchedule(simpleSchedule()
                .withIntervalInMinutes(5).withRepeatCount(5)).build();
        ft = sch.rescheduleJob(trigger.getKey(),trigger);


    }

    public static void main(String[] args) throws Exception{
        SimpleTrigger2 ex2=new SimpleTrigger2();
        ex2.run();
    }
}
