package ua.com.adr.android.englishforkids;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActionListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_list);

        Button study = (Button) findViewById(R.id.btn_study);
        Button repeat = (Button) findViewById(R.id.btn_repeat);




        View.OnClickListener clickBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.btn_study:
                        Intent intent = new Intent(ActionListActivity.this, StudyActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.btn_repeat:
                        Intent intent2 = new Intent(ActionListActivity.this, RepeatActivity.class);
                        startActivity(intent2);
                        break;
                    default:

                }
            }
        };

        study.setOnClickListener(clickBtn);
        repeat.setOnClickListener(clickBtn);
    }
}
