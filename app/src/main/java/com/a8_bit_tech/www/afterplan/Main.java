package com.a8_bit_tech.www.afterplan;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class Main extends AppCompatActivity {

    Button b_start,b_stop,b_show;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_start = findViewById(R.id.btn_start);
        b_stop = findViewById(R.id.btn_stop);
        b_show = findViewById(R.id.btn_show);
        status = findViewById(R.id.text_status);

        b_start.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                doWork();
            }
        });

        b_stop.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                cancelJob();
            }
        });

        b_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showJobs();
            }
        });
    }

    public void showJobs(){
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Location location = null;

        if(networkEnabled){
            double longitude=location.getLongitude();
            double latitude=location.getLatitude();
            status.setText("Longitude = "+longitude+"\nLatitude = "+latitude);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void doWork(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS);

        if (result == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(Main.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    1);
        }

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
        {
            startJob();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startJob(){

        ComponentName componentName = new ComponentName(this, CustomJob.class);
        JobInfo info = new JobInfo.Builder(123, componentName).setPeriodic(60*60*1000).build();

        JobScheduler scheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(info);
        if(result == JobScheduler.RESULT_SUCCESS)
        {
            status.setText("(Start) Service successfully started");
        }
        else
        {
            status.setText("(Start) Service could not be started");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void cancelJob(){
        JobScheduler scheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        status.setText("(Stop) Service has been cancelled");
    }
}
