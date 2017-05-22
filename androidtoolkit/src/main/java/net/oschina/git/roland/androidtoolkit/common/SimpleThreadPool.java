package net.oschina.git.roland.androidtoolkit.common;

import android.os.Handler;
import android.os.Looper;

/**
 * 一个简单的线程池
 * Created by Roland on 2017/5/22.
 */

public class SimpleThreadPool {

    private int poolSize = 1;

    private int index = 0;

    private LoopThread[] pool;

    private boolean isDestroyed = false;

    /**
     * 创建指定大小的线程池
     * @param poolSize 线程池大小
     */
    public SimpleThreadPool(int poolSize) {
        this.poolSize = poolSize;
        pool = new LoopThread[poolSize];
        for (int i = 0; i < poolSize; i++) {
            pool[i] = new LoopThread();
            pool[i].start();
        }
    }

    /**
     * 提交一个Runnable到线程池去执行
     */
    public void execute(Runnable runnable) {
        if (isDestroyed) {
            throw new RuntimeException("This SimpleThreadPool has been destroyed");
        } else {
            pool[index % poolSize].post(runnable);
            index++;
        }
    }

    /**
     * 不需要再使用该线程池时调用
     *
     * 停止每一个线程的Looper
     */
    public void destroy() {
        if (!isDestroyed) {
            for (int i = 0; i < poolSize; i++) {
                pool[i].quit();
            }
            isDestroyed = true;
        }
    }


    private static class LoopThread extends Thread {
        private Handler handler;

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            handler = new Handler();
            Looper.loop();
        }

        void post(Runnable runnable) {
            if (handler != null) {
                handler.post(runnable);
            }
        }

        void quit() {
            if (handler != null) {
                handler.getLooper().quit();
            }
        }
    }
}
