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
import java.util.Calendar;
import java.util.Date;
import android.os.Bundle;
import java.lang.System;

public abstract class BaseNotification {
    private static final String TAG = "BaseNotification";
    private static int mId = 666;
    protected Context mContext = null;

    public BaseNotification(Context context) {
        mContext = context;
    }

    abstract protected NotificationCompat.Builder getNotificationBuilder();
    abstract protected Bundle getNotificationArgs();
    abstract protected NotificationService.NotificationType getNotificationType();

    public void show() {
        NotificationCompat.Builder mBuilder = getNotificationBuilder();

        Intent resultIntent = new Intent(mContext, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
            stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
            (NotificationManager)mContext.getSystemService(
                Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(mId, mBuilder.build());
        mId++;
    }

    public void show(Date date) {
        Intent intent = new Intent(mContext, NotificationService.class);
        intent.putExtra(NotificationService.EXTRA_NOTIFICATION_TYPE,
            getNotificationType().ordinal());
        intent.putExtra(NotificationService.EXTRA_NOTIFICATION_ARGS,
            getNotificationArgs());

        PendingIntent pi = PendingIntent.getService(mContext, 0, intent, 0);

        AlarmManager am = (AlarmManager)mContext
            .getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP,
            getNotificationTime(date), pi);
    }

    private long getNotificationTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Log.d(TAG, "getNotificationTime(): currentTimeMillis: " + System.currentTimeMillis()
            + " getTimeInMillis: " + c.getTimeInMillis());
        return c.getTimeInMillis();
    }
}

