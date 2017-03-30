package net.oschina.git.roland.androidtoolkit.tasksubmit;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务队列
 * Created by Roland on 2017/3/28.
 */

public class TaskQueue {

    private static final String TAG = TaskQueue.class.getSimpleName();

    private final Integer LOCK_TASKLIST = 0;

    private List<BaseSubmitTask> taskList = new ArrayList<>();

//    private List<BaseSubmitTask> createdTaskList = new ArrayList<>();
//
//    private List<BaseSubmitTask> uploadingTaskList = new ArrayList<>();
//
//    private List<BaseSubmitTask> pausedTaskList = new ArrayList<>();
//
//    private List<BaseSubmitTask> waitingTaskList = new ArrayList<>();
//
//    private List<BaseSubmitTask> errorTaskList = new ArrayList<>();

    /**
     * 向任务队列中放入一个任务
     * @param task 任务
     */
    public void addTask(BaseSubmitTask task) {
        synchronized (LOCK_TASKLIST) {
            taskList.add(task);
        }
    }

}
