package net.oschina.git.roland.rolandandroidtoolkit.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.oschina.git.roland.androidtoolkit.common.SimpleThreadPool;
import net.oschina.git.roland.rolandandroidtoolkit.R;

public class SimpleThreadPoolActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SimpleThreadPoolActivity.class.getSimpleName();

    private SimpleThreadPool threadPool = new SimpleThreadPool(2);

    private Button btnAdd, btnQuit;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_thread_pool);

        initComp();
        initListener();
        initData();
    }

    private void initComp() {
        TextView tvHeader = (TextView) findViewById(R.id.tvHeader);
        tvHeader.setText(R.string.str_simple_thread_pool);

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnQuit = (Button) findViewById(R.id.btn_quit);
    }

    private void initListener() {
        btnAdd.setOnClickListener(this);
        btnQuit.setOnClickListener(this);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                threadPool.execute(new MyRunnable(String.valueOf(index++)));
                break;

            case R.id.btn_quit:
                threadPool.destroy();
                break;

            default:
                break;
        }
    }

    private static class MyRunnable implements Runnable {

        private String name;

        public MyRunnable(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                Log.d(TAG, "Runnable " + name + " run!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
