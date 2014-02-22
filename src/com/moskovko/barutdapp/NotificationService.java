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
    private static final String EXTRA_NOTIFICATION_ARGS = "notificationArgs";
    private static final String NOTIFICATION_ARGS_GAME = "notificationArgsGame";

    public static enum NotificationType {
        UNKNOWN, GAME_UPCOMING;

        public void showNotification(Context context, Bundle args) {
            switch(this) {
            case GAME_UPCOMING:
                Notification.showScheduleNotification(context,
                    (GameParser.Game)args.getSerializable(
                        NOTIFICATION_ARGS_GAME));
                break;
            }
        }
    }

    public NotificationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int nt = intent.getIntExtra(EXTRA_NOTIFICATION_TYPE, NotificationType.UNKNOWN.ordinal());
        Bundle args = (Bundle)intent.getBundleExtra(EXTRA_NOTIFICATION_ARGS);
        NotificationType.values()[nt].showNotification(this, args);
    }

    public static void scheduleScheduleNotification(Context context, GameParser.Game game) {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationService.class);
        intent.putExtra(EXTRA_NOTIFICATION_TYPE, NotificationType.GAME_UPCOMING.ordinal());
        Bundle args = new Bundle();
        args.putSerializable(NOTIFICATION_ARGS_GAME, game);
        intent.putExtra(EXTRA_NOTIFICATION_ARGS, args);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);
        am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, (SystemClock.elapsedRealtime()
            + (10 * 1000)), pi);
    }
}

