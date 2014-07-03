package Quartz;

import org.quartz.*;
import org.quartz.impl.matchers.KeyMatcher;
import org.quartz.listeners.JobListenerSupport;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 相关联的Job绑定执行.........
 * Created by panrui on 2014/5/31.
 */
public class MyJobListener {
    private Map<JobDetail, Object> jobs = null;
    public MyJobListener() {
    }

    public MyJobListener(Map<JobDetail, Object> jobs) {
        this.jobs = jobs;
    }

    /**
     * 将相关联的Job绑定到一起执行......
     * @param jobs  参数Map的Key为JobDetail,是需要监听的Job,Value为Object,可以是JobDetail,跟在前面的Job后面执行,也可以是Map,递归跟在前面的JobDetail后面执行.......
     * @throws SchedulerException
     */
    public void jobToJobs(Map<JobDetail, Object> jobs) throws SchedulerException {
        Set<Map.Entry<JobDetail, Object>> jobT = jobs.entrySet();
        Iterator<Map.Entry<JobDetail, Object>> itor = jobT.iterator();
        Map.Entry<JobDetail, Object> entry = null;
        Matcher<JobKey> matcher = null;
        final JobDetail[] jobDetail = new JobDetail[1];
        final Object[] obj = new Object[1];
        while (itor.hasNext()) {
            entry = itor.next();
            jobDetail[0] = entry.getKey();
            obj[0] = entry.getValue();
            matcher = KeyMatcher.keyEquals(jobDetail[0].getKey());
            if (obj[0] instanceof JobDetail) {
//               final JobDetail job2=(JobDetail)obj;
                SchedulerSupport.scheduler.getListenerManager().addJobListener(new JobListenerSupport() {
                    @Override
                    public String getName() {
                        return jobDetail[0].getKey().toString();
                    }

                    @Override
                    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
                        super.jobWasExecuted(context, jobException);
                        try {
                            SchedulerSupport.scheduler.addJob((JobDetail) obj[0], true);
                            SchedulerSupport.scheduler.triggerJob(((JobDetail) obj[0]).getKey());
                        } catch (SchedulerException e) {
                            e.printStackTrace();
                        }
                    }
                }, matcher);
            } else {
                Set<Map.Entry<JobDetail, Object>> jobb = ((Map<JobDetail, Object>) obj[0]).entrySet();
                Iterator<Map.Entry<JobDetail, Object>> itorr = jobb.iterator();
                Map.Entry<JobDetail, Object> entryy = null;
                Matcher<JobKey> matcherr = null;
                final JobDetail[] jobDetaill = new JobDetail[1];
                final Object[] objj = new Object[1];
                while (itorr.hasNext()) {
                    entryy = itorr.next();
                    jobDetaill[0] = entryy.getKey();
                    objj[0] = entryy.getValue();
                    matcherr = KeyMatcher.keyEquals(jobDetaill[0].getKey());
                    SchedulerSupport.scheduler.getListenerManager().addJobListener(new JobListenerSupport() {
                        @Override
                        public String getName() {
                            return jobDetail[0].getKey().toString();
                        }

                        @Override
                        public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
                            super.jobWasExecuted(context, jobException);
                            try {
                                SchedulerSupport.scheduler.addJob(jobDetaill[0], true);
                                SchedulerSupport.scheduler.triggerJob(jobDetaill[0].getKey());
                            } catch (SchedulerException e) {
                                e.printStackTrace();
                            }
                        }
                    }, matcher);
                    }
                     jobToJobs((Map<JobDetail, Object>) obj[0]);
                    }
                }
            }
        }

