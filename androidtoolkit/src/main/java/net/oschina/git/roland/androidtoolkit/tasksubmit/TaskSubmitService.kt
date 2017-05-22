package net.oschina.git.roland.androidtoolkit.tasksubmit

import android.app.Service
import android.content.Intent
import android.os.IBinder

import java.util.ArrayList

/**
 * 任务提交服务
 * Created by Roland on 2017/3/28.
 */

class TaskSubmitService : Service(), SubmitTaskProgressListener {

    private val binder: TaskSubmitServiceBinder = TaskSubmitServiceBinder(this)

    private val taskQueue: TaskQueue = TaskQueue()

    private var submitWindow: SubmitWindow? = null

    private val progressListenerList = ArrayList<SubmitTaskProgressListener>()

    private val LOCK_LISTENER = 0

    override fun onBind(intent: Intent): IBinder? {
        if (submitWindow == null) {
            val windowSize = intent.getIntExtra(Constants.INTENT_KEY_WINDOW_SIZE, 1)
            submitWindow = SubmitWindow(this, windowSize, taskQueue)
            submitWindow!!.setProgressListener(this)
        }
        return binder
    }

    /**
     * 新增任务
     */
    fun addTask(task: BaseSubmitTask) {
        taskQueue.addTask(task)
        submitWindow!!.notifyNewTaskAvailable()
    }

    /**
     * 移除任务
     */
    fun removeTask(name: String) {
        taskQueue.changeTaskState(name, Constants.TaskState.STOP)
        taskQueue.removeTask(name)
    }

    /**
     * 注册进度监听
     */
    fun addProgressListener(listener: SubmitTaskProgressListener) {
        synchronized(LOCK_LISTENER) {
            progressListenerList.add(listener)
        }
    }

    /**
     * 移除进度监听
     */
    fun removeProgressListener(listener: SubmitTaskProgressListener) {
        synchronized(LOCK_LISTENER) {
            progressListenerList.remove(listener)
        }
    }

    override fun onProgress(task: BaseSubmitTask) {
        synchronized(LOCK_LISTENER) {
            for (listener in progressListenerList) {
                listener.onProgress(task)
            }
        }
    }

    /**
     * 改变任务状态
     * @param name 任务名
     * *
     * @param state 目标状态
     */
    fun changeTaskState(name: String, state: Constants.TaskState) {
        taskQueue.changeTaskState(name, state)
        if (state === Constants.TaskState.WAITING) {
            submitWindow!!.notifyNewTaskAvailable()
        }
    }
}
