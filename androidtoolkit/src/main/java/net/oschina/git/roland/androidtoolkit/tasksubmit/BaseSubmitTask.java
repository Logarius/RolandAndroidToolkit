package net.oschina.git.roland.androidtoolkit.tasksubmit;

import net.oschina.git.roland.androidtoolkit.tasksubmit.Constants.TaskState;

import java.util.UUID;

/**
 * 基本的提交任务类
 * Created by Roland on 2017/3/28.
 */

public abstract class BaseSubmitTask implements Cloneable, Runnable {

    private static final String TAG = BaseSubmitTask.class.getSimpleName();

    private TaskState taskState = TaskState.WAITING;

    private UUID id = UUID.randomUUID();

    private SubmitTaskProgressListener progressListener;

    private int windowIndex = -1;

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public BaseSubmitTask clone() throws CloneNotSupportedException {
        return (BaseSubmitTask) super.clone();
    }

    public void setProgressListener(SubmitTaskProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    public int getWindowIndex() {
        return windowIndex;
    }

    public void setWindowIndex(int windowIndex) {
        this.windowIndex = windowIndex;
    }
}
