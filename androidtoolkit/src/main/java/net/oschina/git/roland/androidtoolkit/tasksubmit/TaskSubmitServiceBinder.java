package net.oschina.git.roland.androidtoolkit.tasksubmit;

import android.os.Binder;

/**
 * 任务提交服务Binder
 * Created by Roland on 2017/3/28.
 */

public class TaskSubmitServiceBinder extends Binder {

    private static final String TAG = TaskSubmitServiceBinder.class.getSimpleName();

    private TaskSubmitService service;

    public TaskSubmitServiceBinder(TaskSubmitService service) {
        this.service = service;
    }

    public TaskSubmitService getService() {
        return service;
    }
}
