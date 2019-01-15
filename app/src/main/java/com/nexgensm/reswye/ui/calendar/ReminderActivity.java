package com.nexgensm.reswye.ui.calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nexgensm.reswye.AlarmReceiver;
import com.nexgensm.reswye.R;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;


import java.util.Calendar;


import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ReminderActivity extends Activity {

    CollapsibleCalendar collapsibleCalendar;
    EditText snooze;
    DatePicker pickerDate;
    TimePicker pickerTime;
    Button buttonSetAlarm;
    TextView info;
  //  int snoozeValue;
    final static int RQS_1 = 1;
    AlarmManager  alarmManager;
    public Activity activity;
    String  monthName;
    String datemonth;
    String datestr;
    int day;
    int monthc,snoozeValue;
    int yearc;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Snooze = "snoozekey";

    public void onAttach(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder);


        info = (TextView)findViewById(R.id.info);
     //   pickerDate = (DatePicker)findViewById(R.id.pickerdate);
        pickerTime = (TimePicker)findViewById(R.id.pickertime);
       snooze = (EditText)findViewById(R.id.snooze);

        collapsibleCalendar = (CollapsibleCalendar)findViewById(R.id.calendar);
        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect(int date,int month,int year ) {
                datestr = Integer.toString(date);
               // datestr = date;
               // Toast.makeText(getApplicationContext(),Integer.toString(month),Toast.LENGTH_SHORT).show();
                getMonthShortName(month);
                 day=date;
                 monthc=month;
                 yearc=year;
                datemonth = monthName+" "+datestr;
                Toast.makeText(getApplicationContext(),datemonth,Toast.LENGTH_SHORT).show();

            }
        });

        Calendar now = Calendar.getInstance();
        ImageView backbtn= (ImageView)findViewById(R.id.set_rem_close);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Intent in=new Intent(ReminderActivity.this,AddAppointmentActivity.class);
               // startActivity(in);
            }
        });


        pickerTime.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        pickerTime.setCurrentMinute(now.get(Calendar.MINUTE));

        buttonSetAlarm = (Button)findViewById(R.id.remainderbtn);
        buttonSetAlarm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                Calendar current = Calendar.getInstance();
             //   int y=yearc;
                String SnoozeTime=snooze.getText().toString();
                snoozeValue=Integer.parseInt(SnoozeTime);
                sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(Snooze,snoozeValue );
                editor.commit();

                Calendar cal = Calendar.getInstance();
                   cal.set( yearc,
                           monthc,
                           day,
                           pickerTime.getCurrentHour(),
                           pickerTime.getCurrentMinute(),
                           00);
//                cal.set(pickerDate.getYear(),
//                        pickerDate.getMonth(),
//                        pickerDate.getDayOfMonth(),
//                        pickerTime.getCurrentHour(),
//                        pickerTime.getCurrentMinute(),
//                        00);
//               pickerTime.getCurrentMinute()+snoozeValue;
                if(cal.compareTo(current) <= 0){
                    //The set Date/Time already passed
                    Toast.makeText(getApplicationContext(), "Invalid Date/Time", Toast.LENGTH_LONG).show();
                }else{
                 setAlarm(cal);

                }


            }});

    }

    private void setAlarm(Calendar targetCal){
//
//        info.setText("\n\n***\n"
//                + "Alarm is set@ " + targetCal.getTime() + "\n"
//                + "***\n");
        Toast.makeText(getApplicationContext(), "Alarm is set @"+targetCal.getTime(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }
//    public void snoozeAlarm()
//    {
//       try{
//             Intent in = new Intent(getBaseContext(), AlarmReceiver.class);
////             PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, in, PendingIntent.FLAG_UPDATE_CURRENT);
//           PendingIntent pendingIn = PendingIntent.getBroadcast(getBaseContext(), RQS_1, in, 0);
//            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//           alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 2*1000, pendingIn);
//
//          }
//       catch (Exception e)
//       {
//             e.printStackTrace();
//       }
//
//   }

    private   String getMonthShortName(int monthNumber)
    {


        if(monthNumber>=0 && monthNumber<12)
            try
            {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, monthNumber);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
               // simpleDateFormat.setCalendar ;
              monthName = simpleDateFormat.format(calendar.getTime());

               // Toast.makeText(getApplicationContext(),monthName,Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                if(e!=null)
                    e.printStackTrace();
            }
        return monthName;
    }
}

