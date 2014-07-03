package com.hy.wyy.quartz.test;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import com.hy.wyy.quartz.test.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;

public class QuarzTest {

    /**
     * @param args
     */
    public static void main(String[] args) throws ParseException {

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = null;
        try {
            sched = sf.getScheduler();


        // jobs can be scheduled before sched.start() has been called

        // job 1 will run every 20 seconds
        String groupName = "group1";
        JobDetail job = newJob(SimpleJob.class)
                .withIdentity("job1", groupName)
                .build();

        CronTrigger trigger = newTrigger()
                .withIdentity("trigger1", groupName)
                .withSchedule(cronSchedule("0/20 * * * * ?"))
                .build();

        sched.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}