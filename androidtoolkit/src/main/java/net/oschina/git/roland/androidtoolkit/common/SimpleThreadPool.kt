package net.oschina.git.roland.androidtoolkit.common

import android.os.Handler
import android.os.Looper

/**
 * 一个简单的线程池
 * Created by Roland on 2017/5/22.
 */

class SimpleThreadPool(val poolSize: Int) {

    private var index = 0

    private var pool: Array<LoopThread?>

    private var isDestroyed = false

    init {
        pool = arrayOfNulls<LoopThread>(poolSize)
        for (i in 0..poolSize - 1) {
            pool[i] = LoopThread()
            pool[i]!!.start()
        }
    }

    /**
     * 提交一个Runnable到线程池去执行
     */
    fun execute(runnable: Runnable) {
        if (isDestroyed) {
            throw RuntimeException("This SimpleThreadPool has been destroyed")
        } else {
            pool[index]!!.post(runnable)
            index = (index + 1) % poolSize
        }
    }

    /**
     * 不需要再使用该线程池时调用

     * 停止每一个线程的Looper
     */
    fun destroy() {
        if (!isDestroyed) {
            for (i in 0..poolSize - 1) {
                pool[i]!!.quit()
            }
            isDestroyed = true
        }
    }


    private class LoopThread : Thread() {
        private var handler: Handler? = null

        override fun run() {
            Looper.prepare()
            handler = Handler()
            Looper.loop()
        }

        internal fun post(runnable: Runnable) {
            handler?.post(runnable)
        }

        internal fun quit() {
            handler?.looper?.quit()
        }
    }
}
