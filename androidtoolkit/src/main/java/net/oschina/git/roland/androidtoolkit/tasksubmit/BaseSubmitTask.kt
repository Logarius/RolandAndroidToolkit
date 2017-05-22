package net.oschina.git.roland.androidtoolkit.tasksubmit

import net.oschina.git.roland.androidtoolkit.common.StringUtils
import net.oschina.git.roland.androidtoolkit.tasksubmit.Constants.TaskState

/**
 * 基本的提交任务类
 * Created by Roland on 2017/3/28.
 */

abstract class BaseSubmitTask(var name: String) : Cloneable, Runnable {

    protected var TAG = javaClass.simpleName

    internal var taskState = TaskState.WAITING

    internal var progressListener: SubmitTaskProgressListener? = null

    internal var windowIndex = -1

    var maxProgress = 0
        protected set

    var step = 0
        protected set

    var result = false
        protected set

    init {
        if (StringUtils.isEmpty(name)) {
            name = TAG
        }
    }

    protected fun finish() {
        if (step == maxProgress) {
            taskState = TaskState.FINISH
            result = true
        }

        progressListener?.onProgress(this)
    }

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): BaseSubmitTask {
        return super.clone() as BaseSubmitTask
    }
}
