package com.a8_bit_tech.www.afterplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    TextView text;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread myThread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(1000);
                    Intent intent =new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    finish();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally {
                    Intent intent =new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        myThread.start();
    }
}
