package net.oschina.git.roland.rolandandroidtoolkit.validation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import net.oschina.git.roland.androidtoolkit.validation.BaseValidationTask;
import net.oschina.git.roland.androidtoolkit.validation.OnValidationFinishListener;
import net.oschina.git.roland.androidtoolkit.validation.ValidationTaskQueue;
import net.oschina.git.roland.rolandandroidtoolkit.R;

public class ValidationActivity extends AppCompatActivity {

    private static final String TAG = ValidationActivity.class.getSimpleName();

    private Button btnStart;

    private ValidationTaskQueue taskQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);

        initComp();
        initListener();
        initData();
    }

    private void initComp() {
        btnStart = (Button) findViewById(R.id.btn_start);

        taskQueue = new ValidationTaskQueue(this);
    }

    private void initListener() {
        btnStart.setOnClickListener(onClickListener);

        taskQueue.setOnValidationFinishListener(onValidationFinishListener);
    }

    private void initData() {
        taskQueue.addTask(new MyValidationTask("Task 1"));
        taskQueue.addTask(new MyValidationTask("Task 2"));
        taskQueue.addTask(new MyValidationTask("Task 3"));
        taskQueue.addTask(new MyValidationTask("Task 4"));
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_start:
                    taskQueue.start();
                    break;
            }
        }
    };

    private OnValidationFinishListener onValidationFinishListener = new OnValidationFinishListener() {
        @Override
        public void onValidationFinish(boolean valid) {
            Log.d(TAG, "Validation finish result: " + valid);
        }

        @Override
        public void onTaskFinish(BaseValidationTask task) {
            Log.d(TAG, "Task " + task.getName() + " finish result: " + task.getResult());
        }
    };
}
