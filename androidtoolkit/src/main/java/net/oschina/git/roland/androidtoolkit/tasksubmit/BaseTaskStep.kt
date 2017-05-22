package net.oschina.git.roland.androidtoolkit.tasksubmit

/**
 * Created by Roland on 2017/5/17.
 */

abstract class BaseTaskStep {

    var progressListener: StepProgressListener? = null

    var result = false
        protected set

    abstract fun start()

    protected fun finish(result: Boolean) {
        this.result = result
        progressListener?.onStepFinish(this)
    }
}
