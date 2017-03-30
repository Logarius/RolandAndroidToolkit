package net.oschina.git.roland.androidtoolkit.tasksubmit;

import net.oschina.git.roland.androidtoolkit.tasksubmit.Constants.TaskState;

/**
 * 基本的提交任务类
 * Created by Roland on 2017/3/28.
 */

public class BaseSubmitTask {

    private static final String TAG = BaseSubmitTask.class.getSimpleName();

    private TaskState taskState = TaskState.CREATED;

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }
}
