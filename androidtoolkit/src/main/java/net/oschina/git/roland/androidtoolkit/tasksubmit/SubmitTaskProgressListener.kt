package net.oschina.git.roland.androidtoolkit.tasksubmit

/**
 * 提交任务进度监听器
 * Created by Roland on 2017/3/31.
 */

interface SubmitTaskProgressListener {
    fun onProgress(task: BaseSubmitTask)
}
