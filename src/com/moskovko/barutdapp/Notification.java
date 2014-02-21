package com.moskovko.barutdapp;

import android.util.Log;
import android.support.v4.app.NotificationCompat;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.content.Context;
import android.app.AlarmManager;
import android.os.SystemClock;

public class Notification {
    public Notification() {
    }

    public static void showScheduleNotification(Context context) {
        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle("My notification")
            .setContentText("Hello World!");

        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
            stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
            (NotificationManager)context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(666, mBuilder.build());
    }
}
