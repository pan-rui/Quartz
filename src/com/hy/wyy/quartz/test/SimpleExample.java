package com.hy.wyy.quartz.test;

import java.text.ParseException;
import java.util.Date;

import org.quartz.CronExpression;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerUtils;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.quartz.DateBuilder.*;

public class SimpleExample {

	Logger logger = LoggerFactory.getLogger(SimpleExample.class);

	public void run() throws ParseException {
		logger.info("initialzation");
		try {
			int count = 1;
			// 调度器
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			Date runTime = evenMinuteDate(new Date());

			// jobdetail
			JobBuilder jobBuilder = JobBuilder.newJob(DemoForQuartz.class);// DemoForQuartz.class要被执行的java类
			JobDetail detail = new JobDetailImpl();
			JobDetail jobDetail = (JobDetail) jobBuilder.withIdentity(
					"job" + count, scheduler.getSchedulerInstanceId()).build();// "job1"
			// job名，"group1"job组，可以为null（使用缺省组）scheduler.DEFAULT_GROUP
			jobDetail.getJobDataMap().put("Tester1", "tester1");
			// 建立触发器
			// TriggerBuilder triggerBuilder = (TriggerBuilder) TriggerBuilder
			// .newTrigger().startAt(new Date()).endAt(null);
			SimpleTriggerImpl simpleTriggerImpl = new SimpleTriggerImpl();
			simpleTriggerImpl.setName("simpleTrigger");
			simpleTriggerImpl.setFireInstanceId("simpleTrigger1");
			simpleTriggerImpl.setGroup("group" + count);
			simpleTriggerImpl.setStartTime(new Date());
			simpleTriggerImpl.setEndTime(null);
			simpleTriggerImpl.setRepeatCount(10);
			simpleTriggerImpl.setRepeatInterval(5L * 1000L);

			CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
			cronTriggerImpl.setName("cronTriggerImpl");
			cronTriggerImpl.setCronExpression("0-59 * * ? * *");
			// 设置调度
			// scheduler.scheduleJob(jobDetail, simpleTriggerImpl);
			scheduler.scheduleJob(jobDetail, cronTriggerImpl);
			// scheduler.rescheduleJob(simpleTriggerImpl.getKey(),
			// simpleTriggerImpl);
			// start scheduler
			scheduler.start();
			// try {
			// Thread.sleep(65L * 1000);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// shutdown
			// logger.info("----------shutdown--------");

			// scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleExample simpleExample = new SimpleExample();
		try {
			simpleExample.run();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
