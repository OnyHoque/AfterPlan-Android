package com.a8_bit_tech.www.afterplan;


import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {


    Button bt1 ,bt2 ,bt3 ,bt4 ,bt6 ,getAll ,btStartStop;
    TextView t1,t2,t3,t4,t6 ,text_phone, text_backup ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        t1 = findViewById(R.id.text_stat1);
        t2 = findViewById(R.id.text_stat2);
        t3 = findViewById(R.id.text_stat3);
        t4 = findViewById(R.id.text_stat4);
        t6 = findViewById(R.id.text_stat6);
        text_phone = findViewById(R.id.text_phoneNo);
        text_backup = findViewById(R.id.text_backupNo);

        CheckPermission();

        getNumber();
        bt1 = findViewById(R.id.b_1);
        bt2 = findViewById(R.id.b_2);
        bt3 = findViewById(R.id.b_3);
        bt4 = findViewById(R.id.b_4);
        bt6 = findViewById(R.id.b_6);
        btStartStop = findViewById(R.id.b_startstop);
        getAll = findViewById(R.id.b_getAll);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                per1();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                per2();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                per3();
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                per4();
            }
        });
        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                per6();
            }
        });
        btStartStop.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                check();
            }
        });
        getAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission();
            }
        });

        text_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop1();
            }
        });
        text_backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop2();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void check(){
        SharedPreferences sharedPreferences = Home.this.getSharedPreferences(getString(R.string.data_file), MODE_PRIVATE);
        String state_ = sharedPreferences.getString(getString(R.string.enable_state), "off");
        if(state_.equals("off"))
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(getString(R.string.enable_state), "on");
            editor.commit();
            startJob();
        }
        else
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(getString(R.string.enable_state), "off");
            editor.commit();
            cancelJob();
        }

    }

    boolean CheckPermission(){
        boolean flag = true;
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED)
        {
            t1.setText("Granted");
        }
        else
        {
            t1.setText("Denied");
            flag = false;
        }

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_BOOT_COMPLETED)== PackageManager.PERMISSION_GRANTED)
        {
            t2.setText("Granted");
        }
        else
        {
            t2.setText("Denied");
            flag = false;
        }

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
        {
            t3.setText("Granted");
        }
        else
        {
            t3.setText("Denied");
            flag = false;
        }

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS)== PackageManager.PERMISSION_GRANTED)
        {
            t4.setText("Granted");
        }
        else
        {
            t4.setText("Denied");
            flag = false;
        }

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED )
        {
            t6.setText("Granted");
        }
        else
        {
            t6.setText("Denied");
            flag = false;
        }

        return flag;
    }


    void getPermission() {
        boolean flag = true;
        flag = per1();
        flag = per2();
        flag = per3();
        flag = per4();
        flag = per6();
        if(flag)
        {
            Toast.makeText(getApplicationContext(), "All permissions granted!", Toast.LENGTH_SHORT).show();
        }
    }
    boolean per1(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_PHONE_STATE)==PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            CheckPermission();
            return true;
        }
        else
            return false;
    }
    boolean per2(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.RECEIVE_BOOT_COMPLETED)==PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, 1);
            CheckPermission();
            return true;
        }
        else
            return false;
    }
    boolean per3(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.SEND_SMS}, 1);
            CheckPermission();
            return true;
        }
        else
            return false;
    }
    boolean per4(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.RECEIVE_SMS)==PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
            CheckPermission();
            return true;
        }
        else
            return false;
    }
    boolean per6(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            CheckPermission();
            return true;
        }
        else
            return false;
    }

    void pop1(){
        Intent intent =new Intent(getApplicationContext(), popup1.class);
        startActivity(intent);
        finish();
    }

    void pop2(){
        Intent intent =new Intent(getApplicationContext(), popup2.class);
        startActivity(intent);
        finish();
    }

    void getNumber(){
        SharedPreferences sharedPreferences = Home.this.getSharedPreferences(getString(R.string.data_file), MODE_PRIVATE);
        String phone = sharedPreferences.getString(getString(R.string.phone_number), "+000 000 000");
        String backup = sharedPreferences.getString(getString(R.string.backup_number), "+000 000 000");
        text_phone.setText(phone);
        text_backup.setText(backup);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startJob(){

        ComponentName componentName = new ComponentName(this, CustomJob.class);
        JobInfo info = new JobInfo.Builder(123, componentName).setPeriodic(60*60*1000).build();

        JobScheduler scheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(info);
        if(result == JobScheduler.RESULT_SUCCESS)
        {
            Toast.makeText(getApplicationContext(), "Service successfully started", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Service could not started", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void cancelJob(){
        JobScheduler scheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Toast.makeText(getApplicationContext(), "Service has been cancelled", Toast.LENGTH_SHORT).show();
    }
}
