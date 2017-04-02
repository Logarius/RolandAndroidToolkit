package net.oschina.git.roland.rolandandroidtoolkit.validation;

import android.util.Log;

import net.oschina.git.roland.androidtoolkit.validation.BaseValidationTask;

/**
 * Created by eka on 2017/4/2.
 */

public class MyValidationTask extends BaseValidationTask {

    private static final String TAG = MyValidationTask.class.getSimpleName();

    public MyValidationTask(String name) {
        super(name);
    }

    @Override
    public void run() {
        try {
            Log.d(TAG, "Task " + name + " run.");
            Thread.sleep(2000);
            finish(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
