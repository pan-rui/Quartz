package com.hy.wyy.quartz.test;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.annotation.ExceptionProxy;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by panrui on 2014/5/30.
 */
public class SimpleExamplee {
    public void run() throws Exception{
        Logger log= LoggerFactory.getLogger(SimpleExamplee.class);
        log.info("------------- Initializing-----------------");
        SchedulerFactory sf =new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();
        log.info("------------- Initialization Complete--------");
        Date runTime = DateBuilder.evenMinuteDate(new Date());
        log.info("--------Scheduling Job-----------------");
            JobDetail job=newJob(HelloJob.class).withIdentity("j1","g1").build();

            Trigger trigger = newTrigger().withIdentity("trigger1","g1").startAt(runTime)
                .build();
            sched.scheduleJob(job,trigger);
        log.info(job.getKey()+"will run at:" + runTime);

        log.info("-----------waiting 65 seconds.....------------");
        Thread.sleep(65L*1000L);

        log.info("-----------Shutting Down....--------");
        sched.shutdown(true);

    }

    public static void main(String[] args) throws Exception{
        SimpleExamplee examplee=new SimpleExamplee();
        examplee.run();
    }
}
