package net.oschina.git.roland.rolandandroidtoolkit.submittask;

import android.util.Log;

import net.oschina.git.roland.androidtoolkit.tasksubmit.BaseSubmitTask;

/**
 * Created by eka on 2017/4/1.
 */

class MySubmitTask extends BaseSubmitTask {

    MySubmitTask() {
        super(null);
        setMaxProgress(2);
    }

    @Override
    public void run() {
        try {
            Log.d(getTAG(), "Task name " + getName() + " run.");
            Thread.sleep(1000);
            setStep(getStep() + 1);
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
