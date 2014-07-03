package com.hy.wyy.quartz.test;

import org.apache.log4j.spi.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;

import java.util.Date;

/**
 * Created by panrui on 2014/5/30.
 */
public class HelloJob implements Job {
    private static Logger _log= org.slf4j.LoggerFactory.getLogger(HelloJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        _log.info("Hello World! -"+ new Date());
        System.out.println("helloJob running............");
    }
}
