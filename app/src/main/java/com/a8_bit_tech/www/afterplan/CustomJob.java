package com.a8_bit_tech.www.afterplan;

import android.Manifest;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CustomJob extends JobService{

    @Override
    public boolean onStartJob(JobParameters params) {
        Toast.makeText(getApplicationContext(),"Task Started",Toast.LENGTH_SHORT).show();

        doBackgroundWork(params);
        return false;
    }

    public void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {


                if( ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
                {
                    TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String simNumber = telemamanger.getLine1Number();

                    SharedPreferences sharedPreferences = CustomJob.this.getSharedPreferences(getString(R.string.data_file), MODE_PRIVATE);
                    String phone = sharedPreferences.getString(getString(R.string.phone_number), "+000 000 000");
                    String backup = sharedPreferences.getString(getString(R.string.backup_number), "+000 000 000");

                    if(simNumber != null)
                    {
                        if(!simNumber.equals(phone))
                        {
                            try {
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                    GsmCellLocation cellLocation = (GsmCellLocation) telemamanger.getCellLocation();

                                    int cellid= cellLocation.getCid();
                                    int celllac = cellLocation.getLac();
                                    SmsManager smsManager = SmsManager.getDefault();
                                    String message = "Current phone number:"+simNumber+" CID:"+cellid+"  LAC:"+celllac;
                                    smsManager.sendTextMessage(backup, null, message, null, null);

                                }

                            } catch (Exception ex) {

                            }
                        }
                    }



                }



                jobFinished(params, false);

            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(CustomJob.this,"Task Cancelled",Toast.LENGTH_LONG).show();
        return true;
    }
}
