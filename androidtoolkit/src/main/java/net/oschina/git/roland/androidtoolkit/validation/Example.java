package net.oschina.git.roland.androidtoolkit.validation;

import android.content.Context;

/**
 * 示例
 * Created by Roland on 2017/3/27.
 */

public class Example {

    public void main(Context context) {

        ValidationTaskQueue taskQueue = new ValidationTaskQueue(context);
        taskQueue.setOnValidationFinishListener(onValidationFinishListener);

        MyValidationTask task = new MyValidationTask("TaskName", new Object());
        taskQueue.addTask(task);

        taskQueue.start();
    }

    private OnValidationFinishListener onValidationFinishListener = new OnValidationFinishListener() {
        @Override
        public void onValidationFinish(boolean valid) {

        }

        @Override
        public void onTaskFinish(BaseValidationTask task) {

        }
    };

    class MyValidationTask extends BaseValidationTask {

        private Object data;

        public MyValidationTask(String name, Object data) {
            super(name);
            this.data = data;
        }

        @Override
        public void run() {
            finish(validData());
        }

        private boolean validData() {
            /**
             * 对data进行验证
             */
            return true;
        }
    }

}
