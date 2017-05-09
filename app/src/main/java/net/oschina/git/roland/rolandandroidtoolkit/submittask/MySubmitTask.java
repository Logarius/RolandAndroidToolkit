package net.oschina.git.roland.rolandandroidtoolkit.submittask;

import android.util.Log;

import net.oschina.git.roland.androidtoolkit.tasksubmit.BaseSubmitTask;

/**
 * Created by eka on 2017/4/1.
 */

class MySubmitTask extends BaseSubmitTask {

    MySubmitTask() {
        super(null);
        maxProgress = 2;
    }

    @Override
    public void run() {
        try {
            Log.d(TAG, "Task name " + name + " run.");
            Thread.sleep(1000);
            step++;
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
