package com.nexgensm.reswye;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.nexgensm.reswye.ui.calendar.AlarmSnoozeActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Nexmin on 14-03-2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    public static Ringtone ringtone;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();
 // using service class
        Intent i = new Intent(context, AlarmService.class);
        context.startService(i);

        createNotification(context);
    }

    public void createNotification(Context context) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("It's your meeting  time")
                .setContentText("Meeting")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSubText("Tab to cancel the ringtone")
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        //To add a dismiss button
        Intent dismissIntent = new Intent(context, AlarmService.class);
        dismissIntent.setAction(AlarmService.ACTION_DISMISS);

        PendingIntent pendingIntent = PendingIntent.getService(context, 123, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action action = new NotificationCompat.Action
                (android.R.drawable.ic_lock_idle_alarm, "DISMISS", pendingIntent);
        builder.addAction(action);
        // end of setting action button to notification


        Intent intent1 = new Intent(context, AlarmSnoozeActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 123, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pIntent);


        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(123, notification);


    }
}