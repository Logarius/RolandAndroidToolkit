package net.oschina.git.roland.androidtoolkit.validation;

import java.util.ArrayList;
import java.util.List;

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
        if (!isRunning) {
            synchronized (this) {
                if (!isRunning) {
                    onValidationFinishListener = listener;
                }
            }
        }
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
        } else {
            finishValidation(false);
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (0 <= pos && pos < taskList.size()) {
                new Thread(taskList.get(pos++)).start();
            } else {
                finishValidation(true);
            }
            return false;
        }
    });

    private void finishValidation(boolean result) {
        isRunning = false;
        taskList.clear();
        pos = 0;
        if (onValidationFinishListener != null) {
            onValidationFinishListener.onValidationFinish(result);
        }
    }
}
