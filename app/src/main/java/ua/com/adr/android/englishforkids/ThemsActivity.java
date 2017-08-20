package ua.com.adr.android.englishforkids;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thems);

        Button toys = (Button) findViewById(R.id.toys);




        View.OnClickListener clickBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.toys:
                        Intent intent = new Intent(ThemsActivity.this, ActionListActivity.class);
                        startActivity(intent);
                        break;
                    default:

                }
            }
        };

        toys.setOnClickListener(clickBtn);
    }
}
