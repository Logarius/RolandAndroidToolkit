package net.oschina.git.roland.androidtoolkit.tasksubmit;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.oschina.git.roland.androidtoolkit.tasksubmit.Constants.TaskState;

/**
 * 提交窗口
 * Created by Roland on 2017/3/28.
 */

public class SubmitWindow implements SubmitTaskProgressListener {

    private static final String TAG = SubmitWindow.class.getSimpleName();

    public static final int WINDOWS_SIZE_MAX = 5;

    private int windowSize = 1;

    private Context mContext;

    private BaseSubmitTask[] window;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private TaskQueue taskQueue;

    private int nextIndex = 0;

    private final Integer LOCK_WINDOW = 0;

    private SubmitTaskProgressListener progressListener;

    public SubmitWindow(Context context, int windowSize, TaskQueue taskQueue) {
        this.mContext = context;
        this.taskQueue = taskQueue;

        if (windowSize > WINDOWS_SIZE_MAX) {
            this.windowSize = WINDOWS_SIZE_MAX;
        } else if (windowSize < 0) {
            this.windowSize = 1;
        } else {
            this.windowSize = windowSize;
        }
        this.window = new BaseSubmitTask[this.windowSize];

        Log.d(TAG, "Submit Window of size " + windowSize + " created.");
    }

    /**
     * 通知上传窗口有新的任务
     */
    public void notifyNewTaskAvailable() {
        if (nextIndex >= 0) {
            synchronized (LOCK_WINDOW) {
                if (nextIndex >= 0) {
                    fetchAndStart();
                }
            }
        }
    }

    /**
     * 查找下一个可用的窗口位置索引
     */
    private void findNextIndex() {
        if (nextIndex < 0 || window[nextIndex] != null) {
            for (int i = 0; i < windowSize; i++) {
                if (window[i] == null) {
                    nextIndex = i;
                    return;
                }
            }
            nextIndex = -1;
        }
    }

    public void setProgressListener(SubmitTaskProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    @Override
    public void onProgress(BaseSubmitTask task) {
        if (task.getTaskState() == TaskState.UPLOADING) {
            executor.execute(task);
        } else {
            synchronized (LOCK_WINDOW) {
                Log.d(TAG, "Remove task id " + task.getId() + " from window.");
                window[task.getWindowIndex()] = null;
                findNextIndex();
                fetchAndStart();
            }
        }

        if (progressListener != null) {
            progressListener.onProgress(task);
        }
    }

    /**
     * 从任务队列中取得任务并执行
     */
    private void fetchAndStart() {
        if (nextIndex >= 0) {
            BaseSubmitTask task = taskQueue.getTask(TaskState.WAITING);
            if (task != null) {
                Log.d(TAG, "put task id " + task.getId() + "to window " + nextIndex);
                window[nextIndex] = task;
                task.setTaskState(TaskState.UPLOADING);
                task.setWindowIndex(nextIndex);
                task.setProgressListener(this);
                findNextIndex();
                executor.execute(task);
            }
        }
    }
}
