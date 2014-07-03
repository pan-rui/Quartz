package com.hy.wyy.quartz.test;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * Created by panrui on 2014/5/30.
 */
public class quratz1 {

    public static void main(String[] args) throws InterruptedException {
       SchedulerFactory factory=new StdSchedulerFactory();
        try {
        //从工厂里面拿到一个Scheduler实例
            Scheduler scheduler=factory.getScheduler();
            //计算任务的开始时间,DateBuilder.evenMinuteDate方法是取下一个整数分钟
            Date runtime= DateBuilder.evenMinuteDate(new Date());
            //真正执行的任务并不是Job接口的实例,而是用反射的方式实例化的一个JobDetail实例
            JobDetail job  = JobBuilder.newJob(SimpleJob.class).withIdentity("job1","group1")
                    .build();
            //定义一个触发吕,startAt方法定义了任务应当开始的时间
            Trigger trigger=TriggerBuilder.newTrigger().withIdentity("trigger1","group11")
                    .startAt(runtime).build();
            //将任务和Trigger放入Scheduler
            scheduler.scheduleJob(job,trigger);
//            scheduler.scheduleJob(job,trigger);
            scheduler.start();
            Thread.sleep(25L*1000L);
        scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
