package com.moskovko.barutdapp;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.content.Context;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.SystemClock;
import android.os.Bundle;

public class NotificationService extends IntentService {
    private static final String TAG = "NotificationService";
    private static final String EXTRA_NOTIFICATION_TYPE = "notificationType";

    public static enum NotificationType {
        UNKNOWN, GAME_UPCOMING;

        public void showNotification(Context context) {
            switch(this) {
            case GAME_UPCOMING:
                Notification.showScheduleNotification(context);
                break;
            }
        }
    }

    public NotificationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //intent.setExtrasClassLoader(com.moskovko.barutdapp.NotificationService.NotificationType.class.getClassLoader());
        //intent.setExtrasClassLoader(NotificationType.class.getClassLoader());
/*
        Bundle extras = intent.getExtras();
        extras.setClassLoader(NotificationType.UNKNOWN.getDeclaringClass().getClassLoader());
        NotificationType nt = (NotificationType)extras
            .getSerializable(EXTRA_NOTIFICATION_TYPE);
*/
        //Log.d(TAG, "Notification type: " + nt);
/*
        nt.showNotification(this);
*/
    }

    public static void scheduleScheduleNotification(Context context) {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationService.class);
        //intent.setExtrasClassLoader(NotificationType.class.getClassLoader());
        intent.setExtrasClassLoader(NotificationType.GAME_UPCOMING.getDeclaringClass().getClassLoader());
        intent.putExtra(EXTRA_NOTIFICATION_TYPE, NotificationType.GAME_UPCOMING);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);
        am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, (SystemClock.elapsedRealtime()
            + (10 * 1000)), pi);
    }
}

