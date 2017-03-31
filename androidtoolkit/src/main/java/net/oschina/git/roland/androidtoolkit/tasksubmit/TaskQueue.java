package net.oschina.git.roland.androidtoolkit.tasksubmit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        synchronized (LOCK_TASKLIST) {
            taskList.add(task);
        }
    }

    /**
     * 获取一个指定状态的任务
     *
     * @param state 指定状态
     * @return 任务
     */
    public BaseSubmitTask getTask(TaskState state) {
        synchronized (LOCK_TASKLIST) {
            for (BaseSubmitTask task : taskList) {
                if (task.getTaskState() == state) {
                    return task;
                }
            }
            return null;
        }
    }

    /**
     * 从任务队列中删除任务
     *
     * @param task 任务
     */
    public void removeTask(BaseSubmitTask task) {
        synchronized (LOCK_TASKLIST) {
            taskList.remove(task);
        }
    }

    /**
     * 删除指定ID的任务
     *
     * @param id 任务ID
     */
    public void removeTask(UUID id) {
        synchronized (LOCK_TASKLIST) {
            for (BaseSubmitTask task : taskList) {
                if (task.getId() == id) {
                    taskList.remove(task);
                    return;
                }
            }
        }
    }

    /**
     * 获取任务队列的一个副本
     * <p>
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
}
