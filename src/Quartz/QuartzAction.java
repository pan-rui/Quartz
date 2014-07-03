package Quartz;

/**
 * Created by panrui on 2014/5/31.
 */
public enum QuartzAction {
    START("启动一个Job任务"),
    ISSTARTED("检查调度器是否启动"),
    SHUTDOWN("关闭调度器"),
    ADD_JOB("添加相关的Job任务"),
    ADD_JOBS("添加多个相关的Job任务"),
    ADD_TRIGGER("添加相关的触发器"),
    STOP_TRIGGER("停止调度Job任务"),
    STOP_TRIGGERS("停止多个触发器任务"),
    RECOVER_TRIGGER("重置相关的Job任务"),
    PAUSE_JOB("暂停相关的Job任务"),
    PAUSE_JOBS("暂停多个相关的Job任务"),
    PAUSE_TRIGGER("暂停相关的触发器"),
    PAUSE_TRIGGERS("暂停多个相关的触发器"),
    DELETE_JOB("删除相关的Job任务"),
    DELETE_JOBS("删除多个相关的任务"),
    RECOVER_TRIGGERS("恢复多个触发器任务"),
    RECOVER_JOB("恢复相关的Job任务"),
    RECOVER_JOBS("恢复多个相关的Job任务"),
    PAUSE_ALL("暂停调度器中的所有任务"),
    RECOVER_ALL("恢复调度器中的所有任务");
    private String status;
     QuartzAction(String status){
        this.status=status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
