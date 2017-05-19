package net.oschina.git.roland.androidtoolkit.tasksubmit;

import net.oschina.git.roland.androidtoolkit.common.StringUtils;
import net.oschina.git.roland.androidtoolkit.tasksubmit.Constants.TaskState;

/**
 * 基本的提交任务类
 * Created by Roland on 2017/3/28.
 */

public abstract class BaseSubmitTask implements Cloneable, Runnable {

    protected String TAG = getClass().getSimpleName();

    protected TaskState taskState = TaskState.WAITING;

    protected String name;

    protected SubmitTaskProgressListener progressListener;

    protected int windowIndex = -1;

    protected int maxProgress = 0;

    protected int step = 0;

    protected boolean result = false;

    public BaseSubmitTask(String name) {
        if (StringUtils.isEmpty(name)) {
            name = TAG;
        }
        this.name = name;
    }

    protected void finish() {
        if (step == maxProgress) {
            taskState = TaskState.FINISH;
            result = true;
        }

        if (progressListener != null) {
            progressListener.onProgress(this);
        }
    }

    @Override
    public BaseSubmitTask clone() throws CloneNotSupportedException {
        return (BaseSubmitTask) super.clone();
    }

    void setProgressListener(SubmitTaskProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    int getWindowIndex() {
        return windowIndex;
    }

    void setWindowIndex(int windowIndex) {
        this.windowIndex = windowIndex;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public int getStep() {
        return step;
    }

    public boolean getResult() {
        return result;
    }

    TaskState getTaskState() {
        return taskState;
    }

    void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
