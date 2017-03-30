package net.oschina.git.roland.androidtoolkit.tasksubmit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 任务提交服务
 * Created by Roland on 2017/3/28.
 */

public class TaskSubmitService extends Service {

    private static final String TAG = TaskSubmitService.class.getSimpleName();

    private TaskSubmitServiceBinder binder = new TaskSubmitServiceBinder(this);

    private TaskQueue taskQueue = new TaskQueue();

    private SubmitWindow submitWindow = new SubmitWindow(1);


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


}
