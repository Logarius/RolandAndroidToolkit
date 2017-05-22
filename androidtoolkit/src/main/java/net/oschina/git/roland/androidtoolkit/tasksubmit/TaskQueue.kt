package net.oschina.git.roland.androidtoolkit.tasksubmit

import java.util.ArrayList

import net.oschina.git.roland.androidtoolkit.common.StringUtils
import net.oschina.git.roland.androidtoolkit.tasksubmit.Constants.TaskState

/**
 * 任务队列
 * Created by Roland on 2017/3/28.
 */

class TaskQueue {

    private val LOCK_TASKLIST = 0

    private val taskList = ArrayList<BaseSubmitTask>()

    /**
     * 向任务队列中放入一个任务

     * @param task 任务
     */
    fun addTask(task: BaseSubmitTask?) {
        if (task != null) {
            synchronized(LOCK_TASKLIST) {
                taskList.add(task)
            }
        }
    }

    /**
     * 获取一个指定状态的任务

     * @param state 指定状态
     * *
     * @return 任务
     */
    fun getTask(state: TaskState?): BaseSubmitTask? {
        if (state != null) {
            synchronized(LOCK_TASKLIST) {
                for (task in taskList) {
                    if (task.taskState === state) {
                        return task
                    }
                }
            }
        }
        return null
    }

    /**
     * 从任务队列中删除任务

     * @param task 任务
     */
    fun removeTask(task: BaseSubmitTask?) {
        if (task != null) {
            synchronized(LOCK_TASKLIST) {
                taskList.remove(task)
            }
        }
    }

    /**
     * 删除指定ID的任务

     * @param name 任务名
     */
    fun removeTask(name: String) {
        if (!StringUtils.isEmpty(name)) {
            synchronized(LOCK_TASKLIST) {
                for (task in taskList) {
                    if (task.name == name) {
                        taskList.remove(task)
                        return
                    }
                }
            }
        }
    }

    /**
     * 获取任务队列的一个副本

     * 该返回的副本应只用于获取任务队列内容，对副本的操作不会影响队列的运作。

     * @return 任务队列副本
     */
    val taskListClone: List<BaseSubmitTask>
        get() = synchronized(LOCK_TASKLIST) {
            val taskListClone = ArrayList<BaseSubmitTask>()
            try {
                for (task in taskList) {
                    taskListClone.add(task.clone())
                }
            } catch (e: CloneNotSupportedException) {
                e.printStackTrace()
            }

            return taskListClone
        }

    /**
     * 设定指定ID的任务为指定状态
     * @param name 任务名
     * *
     * @param state 指定状态
     */
    fun changeTaskState(name: String, state: TaskState?) {
        if (!StringUtils.isEmpty(name) && state != null) {
            synchronized(LOCK_TASKLIST) {
                for (task in taskList) {
                    if (task.name == name) {
                        task.taskState = state
                        break
                    }
                }
            }
        }
    }

    companion object {

        private val TAG = TaskQueue::class.java.simpleName
    }
}
