package net.oschina.git.roland.androidtoolkit.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 校验任务队列
 */
public class ValidationTaskQueue implements OnValidationFinishListener {

    private static final String TAG = ValidationTaskQueue.class.getSimpleName();

    private Context mContext;

    private List<BaseValidationTask> taskList = new ArrayList<>();

    private int pos = 0;

    private OnValidationFinishListener onValidationFinishListener;

    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    private boolean isRunning = false;

    public ValidationTaskQueue(Context context) {
        mContext = context;
    }

    public void addTask(BaseValidationTask task) {
        if (!isRunning) {
            synchronized (this) {
                if (!isRunning) {
                    task.setOnValidationFinishListener(this);
                    taskList.add(task);
                    Log.d(TAG, "Task " + task.getName() + " added.");
                }
            }
        }
    }

    public void setOnValidationFinishListener(OnValidationFinishListener listener) {
        onValidationFinishListener = listener;
    }

    public void start() {
        if (!isRunning) {
            synchronized (this) {
                if (!isRunning) {
                    Log.d(TAG, "Validation start");
                    isRunning = true;
                    handler.sendEmptyMessage(0);
                }
            }
        }
    }

    @Override
    public void onValidationFinish(boolean valid) {

    }

    @Override
    public void onTaskFinish(BaseValidationTask task) {
        Log.d(TAG, "onTaskFinish name: " + task.getName() + " result: " + String.valueOf(task.getResult()));

        if (onValidationFinishListener != null) {
            onValidationFinishListener.onTaskFinish(task);
        }

        if (task.getResult()) {
            handler.sendEmptyMessage(0);
        } else if (onValidationFinishListener != null) {
            onValidationFinishListener.onValidationFinish(false);
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (pos < taskList.size()) {
                singleThreadExecutor.execute(taskList.get(pos++));
            } else if (onValidationFinishListener != null) {
                onValidationFinishListener.onValidationFinish(true);
            }
            return false;
        }
    });
}
