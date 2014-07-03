package Quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.listeners.JobListenerSupport;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SchemaViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by panrui on 2014/5/30.
 * Quartz调度器工具类
 */
public class SchedulerSupport {
    private static org.slf4j.Logger log= LoggerFactory.getLogger(SchedulerSupport.class);
    public static Scheduler scheduler=null;
    private static DateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static SchedulerSupport schedulerSupport=null;

    private SchedulerSupport() throws SchedulerException{
        if(SchedulerSupport.scheduler==null)
            SchedulerSupport.scheduler = new StdSchedulerFactory().getScheduler();
    }
    public static SchedulerSupport getInstence() throws SchedulerException {
        return schedulerSupport==null?schedulerSupport=new SchedulerSupport():schedulerSupport;
    }

    /**
     * 接收一个Map参数,Key为JobDetail,Value为其对应的Trigger,将Job加入到调度器中并启动调度器
     * @param jobs
     */
    public static void run(Map<JobDetail,Set<? extends Trigger>> jobs) {
        Set<Map.Entry<JobDetail, Set<? extends Trigger>>> entry=jobs.entrySet();
        Iterator<Map.Entry<JobDetail,Set<? extends Trigger>>> itor=entry.iterator();
        try {
            JobDetail job=null;
            Set<? extends Trigger> triggers=null;
        while(itor.hasNext()) {
            Map.Entry<JobDetail, Set<? extends Trigger>> jobEntry = itor.next();
            triggers = jobEntry.getValue();
            job = jobEntry.getKey();
            if (job == null || triggers == null || triggers.isEmpty())
                throw new IllegalArgumentException("job or trigger is null ==> " + job + "and " + triggers.size());
            else {
               scheduler.scheduleJob(job, triggers, true);
                log.info(job.getKey() + " will run at: " + format.format(System.currentTimeMillis()));
                scheduler.start();
            }
//        if(job!=null){
//            scheduler.addJob(job,true);
//            scheduler.triggerJob(job.getKey());
//        }
        }
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
//  启动 停止 恢复 暂停 停止全部 enum

    /**
     * 执行调度器的相关操作
     * @param action 调度器需要执行的操作
     * @param obj 操作的对象
     * @return
     * @throws IllegalArgumentException
     * @throws SchedulerException
     */
    public static boolean schedulerAction(QuartzAction action,Object obj) throws IllegalArgumentException,SchedulerException {
        boolean flag = false;
        switch (action) {
            case START: //启动一个Job任务
                if (obj instanceof JobDetail) {
                    JobDetail job=(JobDetail)obj;
                    scheduler.addJob(job,true);
                    scheduler.triggerJob(job.getKey());
                    printLog(action.toString(),job.getKey().toString());
                }
                else
                new IllegalArgumentException("parmaeters is error ==>"+ action.toString()+" and "+ obj);
                break;
            case ISSTARTED: //检查调度是否启动
                flag = scheduler.isStarted();
                printLog(action.toString(),null);
                return flag;
            case SHUTDOWN: //关闭调度信息
                scheduler.shutdown();
                printLog(action.toString(),null);
                break;
            case ADD_JOB: //添加相关的Job任务
                if (obj instanceof JobDetail) {
                    JobDetail job=(JobDetail)obj;
                    scheduler.addJob(job,true);
                    printLog(action.toString(),job.getKey().toString());
                }else
                    new IllegalArgumentException("parmaeters is error ==>"+ action.toString()+" and "+ obj);
                break;
            case ADD_JOBS://添加多个Job任务
                if (obj instanceof Map)
                    run((Map<JobDetail, Set<? extends Trigger>>) obj);
                else
                    new IllegalArgumentException("parmaeters is error ==>"+ action.toString()+" and "+ obj);
                break;
            case ADD_TRIGGER: //添加相关的触发器
                if(obj instanceof Trigger) {
                    Trigger trigger=(Trigger) obj;
                    Date date = scheduler.scheduleJob(trigger);
                    printLog(action.toString(),trigger.getKey().toString());
                }else
                    new IllegalArgumentException("parmaeters is error ==>"+ action.toString()+" and "+ obj);
                break;
            case STOP_TRIGGER://停止调度Job任务
                if(obj instanceof TriggerKey) {
                    TriggerKey triggerKey=(TriggerKey)obj;
                    scheduler.unscheduleJob(triggerKey);
                    printLog(action.toString(), triggerKey.toString());
                }else
                    new IllegalArgumentException("parmaeters is error ==>"+ action.toString()+" and "+ obj);
                break;
            case STOP_TRIGGERS://停止多个调度器
                if((obj instanceof List) && !((List) obj).isEmpty()){
                    if(((List)obj).get(0) instanceof TriggerKey) {
                        List<TriggerKey> triggerKeys = (List<TriggerKey>) obj;
                        scheduler.unscheduleJobs(triggerKeys);
                        printLog(action.toString(),"个数为:"+triggerKeys.size());
                    }
                    }else
                    new IllegalArgumentException("parmaeters is error ==>"+ action.toString()+" and "+ obj);
                break;
            case RECOVER_TRIGGER: //重置相关的Job任务
                if(obj instanceof Trigger) {
                    Trigger trigger = (Trigger) obj;
                    scheduler.rescheduleJob(trigger.getKey(), trigger);
                    printLog(action.toString(), trigger.getKey().toString());
                }else
                    new IllegalArgumentException("parmaeters is error ==>"+ action.toString()+" and "+ obj);
                break;
            case PAUSE_JOB: //暂停一个Job任务
                if(obj instanceof JobKey) {
                    scheduler.pauseJob((JobKey) obj);
                    printLog(action.toString(), obj.toString());
                }else
                    new IllegalArgumentException("parmaeters is error ==>"+ action.toString()+" and "+ obj);
                break;
            case PAUSE_JOBS://暂停多个Job任务
                if(obj instanceof GroupMatcher) {
                    scheduler.pauseJobs((GroupMatcher<JobKey>) obj);
                    printLog(action.toString(), null);
                }else
                if(obj instanceof JobKey) {
                    scheduler.deleteJob((JobKey) obj);
                    printLog(action.toString(), obj.toString());
                }else
                    new IllegalArgumentException("parmaeters is error ==>"+ action.toString()+" and "+ obj);
                break;
            case PAUSE_TRIGGER://暂停相关的触发器
                if (obj instanceof TriggerKey) {
                    scheduler.pauseTrigger((TriggerKey) obj);
                    printLog(action.toString(), obj.toString());
                }else
                if(obj instanceof JobKey) {
                    scheduler.deleteJob((JobKey) obj);
                    printLog(action.toString(), obj.toString());
                }else
                    new IllegalArgumentException("parmaeters is error ==>"+ action.toString()+" and "+ obj);
                break;
            case PAUSE_TRIGGERS: //暂停多个相关的触发器
                if(obj instanceof GroupMatcher) {
                    scheduler.pauseTriggers((GroupMatcher<TriggerKey>) obj);
                    printLog(action.toString(), null);
                }else
                if(obj instanceof JobKey) {
                    scheduler.deleteJob((JobKey) obj);
                    printLog(action.toString(), obj.toString());
                }else
                    new IllegalArgumentException("parmaeters is error ==>"+ action.toString()+" and "+ obj);
                break;
            case DELETE_JOB://删除相关的Job任务
                if(obj instanceof JobKey) {
                    scheduler.deleteJob((JobKey) obj);
                    printLog(action.toString(), obj.toString());
                }else
                    new IllegalArgumentException("parmaeters is error ==>"+ action.toString()+" and "+ obj);
                break;
            case DELETE_JOBS: //删除相关的多个Job任务
                if((obj instanceof List) && !((List) obj).isEmpty()){
                    if(((List)obj).get(0) instanceof JobKey) {
                        List<JobKey> jobKeys = (List<JobKey>) obj;
                        scheduler.deleteJobs(jobKeys);
                        printLog(action.toString(), "个数为:" + jobKeys.size());
                    }
                }else
                    new IllegalArgumentException("parmaeters is error ==>"+ action.toString()+" and "+ obj);
                break;
            case RECOVER_TRIGGERS: //恢复多个触发器
                if(obj instanceof GroupMatcher) {
                    scheduler.resumeTriggers((GroupMatcher<TriggerKey>) obj);
                    printLog(action.toString(), null);
                }else
                    new IllegalArgumentException("parmaeters is error ==>"+ action.toString()+" and "+ obj);
                break;
            case RECOVER_JOB: //恢复相关的Job任务
                if(obj instanceof JobKey) {
                    scheduler.resumeJob((JobKey) obj);
                    printLog(action.toString(), obj.toString());
                }else
                    new IllegalArgumentException("parmaeters is error ==>"+ action.toString()+" and "+ obj);
                break;
            case RECOVER_JOBS: //恢复多个相关的Job任务
                if(obj instanceof GroupMatcher) {
                    scheduler.resumeJobs((GroupMatcher<JobKey>) obj);
                    printLog(action.toString(), null);
                }else
                    new IllegalArgumentException("parmaeters is error ==>"+ action.toString()+" and "+ obj);
                break;
            case PAUSE_ALL: //暂停所有的调度任务
                scheduler.pauseAll();
                printLog(action.toString(),null);
                break;
            case RECOVER_ALL://恢复调度器中的所有任务
                scheduler.resumeAll();
                printLog(action.toString(),null);
                break;
        }
        return flag;
    }

    /**
     * 打印日志信息
     * @param action
     * @param objName
     */
public static void printLog(String action,String objName) {
    log.info("调度器执行的操作为=>" + action + ", 操作对象为=>" + objName + ", 操作时间为=>" +
            format.format(System.currentTimeMillis()));
}

//    串联........
//    JobListenerSupport

}
