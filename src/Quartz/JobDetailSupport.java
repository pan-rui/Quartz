package Quartz;

import com.hy.wyy.quartz.test.SimpleJob;
import org.quartz.Job;
import org.quartz.JobDetail;

import java.util.IllegalFormatException;

import static org.quartz.JobBuilder.newJob;

/**
 * Created by panrui on 2014/5/30.
 */
public class JobDetailSupport {
    private static String jobName=null;
    private static int count=1;

    public static String getJobName() {
        return jobName;
    }

    public static void setJobName(String jobName) {
        JobDetailSupport.jobName = jobName;
    }

    public JobDetailSupport(){}
    public JobDetailSupport(String jobName){
        this.jobName=jobName;
    }

    /**
     * 获取JobDetail
     * @param job 参数为Job实例
     * @return JobDetail
     */
    public static JobDetail JobDetailBuilder(Job job){
        JobDetail jobDetail= newJob(job.getClass()).withIdentity(jobName!=null?jobName:"job"+count,"group"+TriggerSupport.count)
                .build();
        return jobDetail;
    }

    /**
     * 获取JobDetail
     * @param jobClass 参数为JobDetail的类路径完全限定名
     * @return JobDetail
     * @throws IllegalArgumentException
     */
    public static JobDetail JobDetailBuilder(String jobClass) throws IllegalArgumentException{
        JobDetail jobDetail= null;
        try {
        Class clazz=Class.forName(jobClass);
            if(clazz.isInstance(Job.class))
            jobDetail = newJob((Class<? extends Job>) Class.forName(jobClass)).withIdentity(jobName!=null?jobName:"job"+count,"group"+ TriggerSupport.count)
                    .build();
            else
                throw new IllegalArgumentException("jobClass is error:"+jobClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return jobDetail;
    }
}
