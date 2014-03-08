package com.moskovko.barutdapp;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.content.Context;
import android.os.Bundle;

public class NotificationService extends IntentService {
    private static final String TAG = "NotificationService";

    public static final String EXTRA_NOTIFICATION_TYPE = "notificationType";
    public static final String EXTRA_NOTIFICATION_ARGS = "notificationArgs";
    public static final String NOTIFICATION_ARGS_GAME = "notificationArgsGame";

    public static enum NotificationType {
        UNKNOWN, GAME_UPCOMING;

        public void showNotification(Context context, Bundle args) {
            switch(this) {
            case GAME_UPCOMING:
                new ScheduleNotification(context, 
                    (GameParser.Game)args.getSerializable(
                        NOTIFICATION_ARGS_GAME)).show();
                break;
            }
        }
    }

    public NotificationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int nt = intent.getIntExtra(EXTRA_NOTIFICATION_TYPE,
            NotificationType.UNKNOWN.ordinal());
        Bundle args = (Bundle)intent.getBundleExtra(EXTRA_NOTIFICATION_ARGS);
        NotificationType.values()[nt].showNotification(this, args);
    }
}

