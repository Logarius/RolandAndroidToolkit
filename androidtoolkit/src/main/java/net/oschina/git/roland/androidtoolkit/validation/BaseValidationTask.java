package net.oschina.git.roland.androidtoolkit.validation;

import android.util.Log;

/**
 * 基础的校验任务类
 */
public abstract class BaseValidationTask implements Runnable {

    private static final String TAG = BaseValidationTask.class.getSimpleName();

    protected String name;

    protected boolean isRunning = false;

    protected boolean finished = false;

    protected boolean result = false;

    protected int failCode = -1;

    protected OnValidationFinishListener onValidationFinishListener;

    public BaseValidationTask(String name) {
        this.name = name;
    }

    protected void finish(boolean valid) {
        Log.d(TAG, "task " + name + "finish result = " + String.valueOf(valid));
        result = valid;
        finished = true;
        isRunning = false;
        if (onValidationFinishListener != null) {
            onValidationFinishListener.onTaskFinish(this);
        }
    }

    public void setOnValidationFinishListener(OnValidationFinishListener listener) {
        onValidationFinishListener = listener;
    }


    public String getName() {
        return name;
    }

    public boolean getResult() {
        return result;
    }

    public boolean isRunning() {
        return !isRunning;
    }

    public boolean finished() {
        return finished;
    }

    public int getFailCode() {
        return failCode;
    }
}
