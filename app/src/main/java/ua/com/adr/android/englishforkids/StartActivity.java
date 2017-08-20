package ua.com.adr.android.englishforkids;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class StartActivity extends AppCompatActivity {

    String mLang;
    private Locale newLocale;
    TextView txtV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton ruBtn = (ImageButton) findViewById(R.id.imageButtonRoss);
        ImageButton uaBtn = (ImageButton) findViewById(R.id.imageButtonUkr);

        Button exit = (Button) findViewById(R.id.exit);
        Button start = (Button) findViewById(R.id.btnStart);




        View.OnClickListener clickBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.imageButtonRoss:
                        setLocale("ru");
                        break;
                    case R.id.imageButtonUkr:
                        setLocale("ua");
                        break;
                    case R.id.btnStart:
                        Intent intent = new Intent(StartActivity.this, ThemsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.exit:
                        finishAll();
                        break;
                    default:

                }
            }
        };
        ruBtn.setOnClickListener(clickBtn);
        uaBtn.setOnClickListener(clickBtn);
        exit.setOnClickListener(clickBtn);
        start.setOnClickListener(clickBtn);

    }
    public void finishAll() {
        finishAffinity();
    }

    void setLocale(String mLang) {
        newLocale = new Locale(mLang);
        Locale.setDefault(newLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = newLocale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        recreate();

    }
}