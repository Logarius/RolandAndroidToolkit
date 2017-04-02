package net.oschina.git.roland.rolandandroidtoolkit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.oschina.git.roland.rolandandroidtoolkit.submittask.SubmitTaskActivity;
import net.oschina.git.roland.rolandandroidtoolkit.validation.ValidationActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnSubmitTask;

    private Button btnValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSubmitTask = (Button) findViewById(R.id.btn_submit_task);
        btnValidation = (Button) findViewById(R.id.btn_validation);

        btnSubmitTask.setOnClickListener(onClickListener);
        btnValidation.setOnClickListener(onClickListener);
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

                default:
                    break;
            }

            if (it != null) {
                startActivity(it);
            }
        }
    };
}
