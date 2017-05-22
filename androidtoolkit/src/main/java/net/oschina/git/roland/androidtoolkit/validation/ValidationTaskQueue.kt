package net.oschina.git.roland.androidtoolkit.validation

import java.util.ArrayList

import android.content.Context
import android.os.Handler
import android.util.Log

/**
 * 校验任务队列
 */
class ValidationTaskQueue(mContext: Context) : OnValidationFinishListener {

    private val taskList = ArrayList<BaseValidationTask>()

    private var pos = 0

    private var onValidationFinishListener: OnValidationFinishListener? = null

    private var isRunning = false

    fun addTask(task: BaseValidationTask) {
        if (!isRunning) {
            synchronized(this) {
                if (!isRunning) {
                    task.setOnValidationFinishListener(this)
                    taskList.add(task)
                    Log.d(TAG, "Task ${task.name} added.")
                }
            }
        }
    }

    fun setOnValidationFinishListener(listener: OnValidationFinishListener) {
        if (!isRunning) {
            synchronized(this) {
                if (!isRunning) {
                    onValidationFinishListener = listener
                }
            }
        }
    }

    fun start() {
        if (!isRunning) {
            synchronized(this) {
                if (!isRunning) {
                    Log.d(TAG, "Validation start")
                    isRunning = true
                    handler.sendEmptyMessage(0)
                }
            }
        }
    }

    override fun onValidationFinish(valid: Boolean) {

    }

    override fun onTaskFinish(task: BaseValidationTask) {
        Log.d(TAG, "onTaskFinish name: ${task.name} result: ${task.result}")

        onValidationFinishListener?.onTaskFinish(task)

        if (task.result) {
            handler.sendEmptyMessage(0)
        } else {
            finishValidation(false)
        }
    }

    private val handler = Handler(Handler.Callback {
        if (pos in 0 until taskList.size) {
            Thread(taskList[pos++]).start()
        } else {
            finishValidation(true)
        }
        false
    })

    private fun finishValidation(result: Boolean) {
        isRunning = false
        taskList.clear()
        pos = 0
        onValidationFinishListener?.onValidationFinish(result)
    }

    companion object {

        private val TAG = ValidationTaskQueue::class.java.simpleName
    }
}
