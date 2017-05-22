package net.oschina.git.roland.androidtoolkit.validation

/**
 * 校验完成监听
 * Created by Roland on 2017/3/27.
 */

interface OnValidationFinishListener {

    /**
     * 校验完成时调用

     * @param valid 校验结果
     */
    fun onValidationFinish(valid: Boolean)

    /**
     * 校验任务完成时调用

     * @param task 校验任务
     */
    fun onTaskFinish(task: BaseValidationTask)
}
