package net.oschina.git.roland.androidtoolkit.validation

import android.util.Log

/**
 * 基础的校验任务类
 */
abstract class BaseValidationTask(var name: String) : Runnable {

    protected var bRunning = false

    protected var finished = false

    var result = false
        protected set

    var failCode = -1
        protected set

    protected var validationFinishListener: OnValidationFinishListener? = null

    protected fun finish(valid: Boolean) {
        Log.d(TAG, "task " + name + " finish result = " + valid.toString())
        result = valid
        finished = true
        bRunning = false
        if (validationFinishListener != null) {
            validationFinishListener!!.onTaskFinish(this)
        }
    }

    fun isRunning(): Boolean {
        return !bRunning
    }

    fun finished(): Boolean {
        return finished
    }

    fun setOnValidationFinishListener(listener: OnValidationFinishListener) {
        validationFinishListener = listener
    }

    companion object {

        private val TAG = BaseValidationTask::class.java.simpleName
    }
}
