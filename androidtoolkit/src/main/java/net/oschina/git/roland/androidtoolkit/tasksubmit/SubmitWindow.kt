package net.oschina.git.roland.androidtoolkit.tasksubmit

import android.content.Context
import android.util.Log

/**
 * 提交窗口
 * Created by Roland on 2017/3/28.
 */

internal class SubmitWindow(mContext: Context, windowSize: Int, private val taskQueue: TaskQueue) : SubmitTaskProgressListener {

    private var windowSize = 1

    private val window: Array<BaseSubmitTask?>

    private var nextIndex = 0

    private val LOCK_WINDOW = 0

    private var progressListener: SubmitTaskProgressListener? = null

    init {

        if (windowSize > WINDOWS_SIZE_MAX) {
            this.windowSize = WINDOWS_SIZE_MAX
        } else if (windowSize < 0) {
            this.windowSize = 1
        } else {
            this.windowSize = windowSize
        }
        this.window = arrayOfNulls<BaseSubmitTask>(this.windowSize)

        Log.d(TAG, "Submit Window of size ${this.windowSize} created.")
    }

    /**
     * 通知上传窗口有新的任务
     */
    fun notifyNewTaskAvailable() {
        if (nextIndex >= 0) {
            synchronized(LOCK_WINDOW) {
                if (nextIndex >= 0) {
                    fetchAndStart()
                }
            }
        }
    }

    /**
     * 查找下一个可用的窗口位置索引
     */
    private fun findNextIndex() {
        if (nextIndex < 0 || window[nextIndex] != null) {
            for (i in 0..windowSize - 1) {
                if (window[i] == null) {
                    nextIndex = i
                    return
                }
            }
            nextIndex = -1
        }
    }

    fun setProgressListener(progressListener: SubmitTaskProgressListener) {
        this.progressListener = progressListener
    }

    override fun onProgress(task: BaseSubmitTask) {
        if (task.taskState === Constants.TaskState.UPLOADING) {
            Thread(task).run()
        } else {
            synchronized(LOCK_WINDOW) {
                Log.d(TAG, "Remove task name ${task.name} from window.")
                window[task.windowIndex] = null
                nextIndex = task.windowIndex
                fetchAndStart()
            }
        }

        progressListener?.onProgress(task)
    }

    /**
     * 从任务队列中取得任务并执行
     */
    private fun fetchAndStart() {
        if (nextIndex >= 0) {
            val task = taskQueue.getTask(Constants.TaskState.WAITING)
            if (task != null) {
                Log.d(TAG, "put task name ${task.name} to window $nextIndex")
                window[nextIndex] = task
                task.taskState = Constants.TaskState.UPLOADING
                task.windowIndex = nextIndex
                task.progressListener = this
                findNextIndex()
                Thread(task).run()
            }
        }
    }

    companion object {

        private val TAG = SubmitWindow::class.java.simpleName

        val WINDOWS_SIZE_MAX = 5
    }
}
