package com.nexgensm.reswye.ui.calendar;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nexgensm.reswye.AlarmReceiver;
import com.nexgensm.reswye.AlarmService;
import com.nexgensm.reswye.R;

public class AlarmSnoozeActivity extends AppCompatActivity {
    AlarmService alarmService;
    ReminderActivity remainderActivity=new ReminderActivity();
    AlarmManager  alarmManager;
    public Ringtone ringtone;
    final static int RQS_1 = 1;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Snooze = "snoozekey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_snooze);
        ImageView backbtn= (ImageView)findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   finish();
//                Intent in=new Intent(AlarmSnoozeActivity.this,ReminderActivity.class);
//                startActivity(in);
            }
        });

        ImageView stopBtn= (ImageView)findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), AlarmService.class);
                stopService(i);
            }
        });

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        ImageView SnoozeBtn= (ImageView)findViewById(R.id.SnoozeBtn);
        final int SnoozeValue=sharedpreferences.getInt(Snooze,0);

        SnoozeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlarmSnoozeActivity.this, AlarmService.class);
                stopService(i);
                Toast.makeText(getApplicationContext(), "Alarm Snooze", Toast.LENGTH_SHORT).show();

                try{
                    Intent in = new Intent(getApplicationContext(), AlarmReceiver.class);
                    PendingIntent pendingIn = PendingIntent.getBroadcast(getBaseContext(), RQS_1, in, 0);
                    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ SnoozeValue * 60 * 1000, pendingIn);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });



    }


}
