package net.oschina.git.roland.rolandandroidtoolkit.submittask;

import android.util.Log;

import net.oschina.git.roland.androidtoolkit.tasksubmit.BaseSubmitTask;

/**
 * Created by eka on 2017/4/1.
 */

public class MySubmitTask extends BaseSubmitTask {

    private static final String TAG = MySubmitTask.class.getSimpleName();

    public MySubmitTask() {
        maxProgress = 2;
    }

    @Override
    public void run() {
        try {
            Log.d(TAG, "Task id " + id + " run.");
            Thread.sleep(1000);
            step++;
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
