package net.oschina.git.roland.androidtoolkit.tasksubmit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务提交服务
 * Created by Roland on 2017/3/28.
 */

public class TaskSubmitService extends Service implements SubmitTaskProgressListener {

    private static final String TAG = TaskSubmitService.class.getSimpleName();

    private TaskSubmitServiceBinder binder;

    private TaskQueue taskQueue;

    private SubmitWindow submitWindow;

    private List<SubmitTaskProgressListener> progressListenerList = new ArrayList<>();

    private final Integer LOCK_LISTENER = 0;

    public TaskSubmitService() {
        binder = new TaskSubmitServiceBinder(this);
        taskQueue = new TaskQueue();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (submitWindow != null) {
            int windowSize = intent.getIntExtra(Constants.INTENT_KEY_WINDOW_SIZE, 1);
            submitWindow = new SubmitWindow(this, windowSize, taskQueue);
            submitWindow.setProgressListener(this);
        }
        return binder;
    }

    public void addTask(BaseSubmitTask task) {
        taskQueue.addTask(task);
        submitWindow.notifyNewTaskAvailable();
    }

    public void addProgressListener(SubmitTaskProgressListener listener) {
        synchronized (LOCK_LISTENER) {
            progressListenerList.add(listener);
        }
    }

    public void removeProgressListener(SubmitTaskProgressListener listener) {
        synchronized (LOCK_LISTENER) {
            progressListenerList.remove(listener);
        }
    }

    @Override
    public void onProgress(BaseSubmitTask task) {
        synchronized (LOCK_LISTENER) {
            for (SubmitTaskProgressListener listener : progressListenerList) {
                listener.onProgress(task);
            }
        }
    }
}
