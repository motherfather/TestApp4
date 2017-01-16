package app.test.testapp4.app.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import app.test.testapp4.R;
import app.test.testapp4.app.core.service.ListService;
import app.test.testapp4.app.ui.list.ListAsyncTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListAsyncTask.init(this);
        ListService.init(this);

        Button button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ListAsyncTask().start(); // ListAsync 작동을 위한 메서드 실행
            }
        });
    }
}
