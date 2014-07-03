package com.hy.wyy.quartz.test;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import static java.lang.System.out;

/**
 * ʵ��Job�ӿ�ʹ����Ա�ִ��
 * 
 * @author zliao
 * 
 */
public class DemoForQuartz implements Job {
	private static Logger logger = org.slf4j.LoggerFactory
			.getLogger(DemoForQuartz.class);

	/**
	 * JobExecutionContext �ṩ��ҵʵ�������ʱ�����ģ��������ʹ������ķ���
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// arg0.getJobDetail()
		// arg0.getScheduler()
		// arg0.getTrigger()
		JobDataMap jobDataMap = arg0.getJobDetail().getJobDataMap();
        arg0.getMergedJobDataMap();
		String test1 = jobDataMap.getString("Test");
		String test2 = jobDataMap.getString("Test2");
		logger.info("Hello " + test1 + " and " + test2 + new Date());
		out.print("second");
	}
}
