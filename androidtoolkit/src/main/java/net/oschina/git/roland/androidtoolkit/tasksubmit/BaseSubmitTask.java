package net.oschina.git.roland.androidtoolkit.tasksubmit;

import net.oschina.git.roland.androidtoolkit.tasksubmit.Constants.TaskState;

import java.util.UUID;

/**
 * 基本的提交任务类
 * Created by Roland on 2017/3/28.
 */

public abstract class BaseSubmitTask implements Cloneable, Runnable {

    private static final String TAG = BaseSubmitTask.class.getSimpleName();

    protected TaskState taskState = TaskState.WAITING;

    protected UUID id = UUID.randomUUID();

    protected SubmitTaskProgressListener progressListener;

    protected int windowIndex = -1;

    protected int maxProgress = 0;

    protected int step = 0;

    protected boolean result = false;

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

    public SubmitTaskProgressListener getProgressListener() {
        return progressListener;
    }

    public int getWindowIndex() {
        return windowIndex;
    }

    public void setWindowIndex(int windowIndex) {
        this.windowIndex = windowIndex;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void finish() {

        if (step == maxProgress) {
            taskState = TaskState.FINISH;
        }

        if (progressListener != null) {
            progressListener.onProgress(this);
        }
    }
}
