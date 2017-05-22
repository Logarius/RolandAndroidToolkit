package net.oschina.git.roland.androidtoolkit.tasksubmit;

import java.util.ArrayList;
import java.util.List;

import net.oschina.git.roland.androidtoolkit.common.StringUtils;
import net.oschina.git.roland.androidtoolkit.tasksubmit.Constants.TaskState;

/**
 * 任务队列
 * Created by Roland on 2017/3/28.
 */

public class TaskQueue {

    private static final String TAG = TaskQueue.class.getSimpleName();

    private final Integer LOCK_TASKLIST = 0;

    private List<BaseSubmitTask> taskList = new ArrayList<>();

    /**
     * 向任务队列中放入一个任务
     *
     * @param task 任务
     */
    public void addTask(BaseSubmitTask task) {
        if (task != null) {
            synchronized (LOCK_TASKLIST) {
                taskList.add(task);
            }
        }
    }

    /**
     * 获取一个指定状态的任务
     *
     * @param state 指定状态
     * @return 任务
     */
    public BaseSubmitTask getTask(TaskState state) {
        if (state != null) {
            synchronized (LOCK_TASKLIST) {
                for (BaseSubmitTask task : taskList) {
                    if (task.getTaskState() == state) {
                        return task;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 从任务队列中删除任务
     *
     * @param task 任务
     */
    public void removeTask(BaseSubmitTask task) {
        if (task != null) {
            synchronized (LOCK_TASKLIST) {
                taskList.remove(task);
            }
        }
    }

    /**
     * 删除指定ID的任务
     *
     * @param name 任务名
     */
    public void removeTask(String name) {
        if (!StringUtils.INSTANCE.isEmpty(name)) {
            synchronized (LOCK_TASKLIST) {
                for (BaseSubmitTask task : taskList) {
                    if (task.getName().equals(name)) {
                        taskList.remove(task);
                        return;
                    }
                }
            }
        }
    }

    /**
     * 获取任务队列的一个副本
     *
     * 该返回的副本应只用于获取任务队列内容，对副本的操作不会影响队列的运作。
     *
     * @return 任务队列副本
     */
    public List<BaseSubmitTask> getTaskListClone() {
        synchronized (LOCK_TASKLIST) {
            List<BaseSubmitTask> taskListClone = new ArrayList<>();
            try {
                for (BaseSubmitTask task : taskList) {
                    taskListClone.add(task.clone());
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return taskListClone;
        }
    }

    /**
     * 设定指定ID的任务为指定状态
     * @param name 任务名
     * @param state 指定状态
     */
    public void changeTaskState(String name, TaskState state) {
        if (!StringUtils.INSTANCE.isEmpty(name) && state != null) {
            synchronized (LOCK_TASKLIST) {
                for (BaseSubmitTask task : taskList) {
                    if (task.getName().equals(name)) {
                        task.setTaskState(state);
                        break;
                    }
                }
            }
        }
    }
}
