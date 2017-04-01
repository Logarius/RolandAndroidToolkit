package net.oschina.git.roland.rolandandroidtoolkit.submittask;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import net.oschina.git.roland.androidtoolkit.tasksubmit.BaseSubmitTask;
import net.oschina.git.roland.androidtoolkit.tasksubmit.SubmitTaskProgressListener;
import net.oschina.git.roland.androidtoolkit.tasksubmit.TaskSubmitService;
import net.oschina.git.roland.androidtoolkit.tasksubmit.TaskSubmitServiceBinder;
import net.oschina.git.roland.rolandandroidtoolkit.R;

import java.util.Locale;

public class SubmitTaskActivity extends AppCompatActivity {

    private static final String TAG = SubmitTaskActivity.class.getSimpleName();

    private Button btnStart;

    private TaskSubmitService service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_task);

        Intent serviceIntent = new Intent(SubmitTaskActivity.this, TaskSubmitService.class);
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);

        initComp();
        initListener();
    }

    private void initComp() {
        btnStart = (Button) findViewById(R.id.btn_start);
    }

    private void initListener() {
        btnStart.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_start:
                    addTask();
                    break;
            }
        }
    };

    private SubmitTaskProgressListener progressListener = new SubmitTaskProgressListener() {
        @Override
        public void onProgress(BaseSubmitTask task) {
            String format = "Task id %s onProgress %d/%d";
            Log.d(TAG, String.format(Locale.US, format, task.getId(), task.getStep(), task.getMaxProgress()));
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TaskSubmitServiceBinder binder = (TaskSubmitServiceBinder) service;
            SubmitTaskActivity.this.service = binder.getService();
            SubmitTaskActivity.this.service.addProgressListener(progressListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void addTask() {
        if (service != null) {
            MySubmitTask task = new MySubmitTask();
            service.addTask(task);
        }
    }
}
