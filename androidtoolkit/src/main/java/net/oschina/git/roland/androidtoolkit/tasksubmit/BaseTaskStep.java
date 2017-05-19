package net.oschina.git.roland.androidtoolkit.tasksubmit;

/**
 * Created by Roland on 2017/5/17.
 */

public abstract class BaseTaskStep {

    protected StepProgressListener progressListener;

    protected boolean result = false;

    public abstract void start();

    protected void finish(boolean result) {
        this.result = result;
        if (progressListener != null) {
            progressListener.onStepFinish(this);
        }
    }

    public void setProgressListener(StepProgressListener listener) {
        this.progressListener = listener;
    }

    public boolean getResult() {
        return result;
    }
}
