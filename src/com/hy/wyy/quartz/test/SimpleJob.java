package com.hy.wyy.quartz.test;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by panrui on 2014/5/30.
 */
public class SimpleJob implements Job{
    org.slf4j.Logger log= LoggerFactory.getLogger(SimpleJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // your business logic
        System.out.println("simpleJob runing...........");
        System.out.println("任务正在执行,执行时间:  "+ Calendar.getInstance().getTime());
        JobKey jobKey=jobExecutionContext.getJobDetail().getKey();
        log.info("SimpleJob says:" + jobKey+"executing at "+new Date());
    }
}
