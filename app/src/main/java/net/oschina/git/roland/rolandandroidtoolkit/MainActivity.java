package net.oschina.git.roland.rolandandroidtoolkit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.oschina.git.roland.rolandandroidtoolkit.common.SimpleThreadPoolActivity;
import net.oschina.git.roland.rolandandroidtoolkit.submittask.SubmitTaskActivity;
import net.oschina.git.roland.rolandandroidtoolkit.validation.ValidationActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvHeader = (TextView) findViewById(R.id.tvHeader);
        tvHeader.setText(R.string.str_main);

        Button btnSubmitTask = (Button) findViewById(R.id.btn_submit_task);
        Button btnValidation = (Button) findViewById(R.id.btn_validation);
        Button btnSimpleThreadPool = (Button) findViewById(R.id.btn_simple_thread_pool);

        btnSubmitTask.setOnClickListener(onClickListener);
        btnValidation.setOnClickListener(onClickListener);
        btnSimpleThreadPool.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent it = null;

            switch (v.getId()) {
                case R.id.btn_submit_task:
                    it = new Intent(MainActivity.this, SubmitTaskActivity.class);
                    break;

                case R.id.btn_validation:
                    it = new Intent(MainActivity.this, ValidationActivity.class);
                    break;

                case R.id.btn_simple_thread_pool:
                    it = new Intent(MainActivity.this, SimpleThreadPoolActivity.class);
                    break;

                default:
                    break;
            }

            if (it != null) {
                startActivity(it);
            }
        }
    };
}
