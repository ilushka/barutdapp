package com.moskovko.barutdapp;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class NotificationService extends IntentService {
    private static final String TAG = "NotificationService";

    public static enum NotificationType {
        UNKNOWN, GAME_UPCOMING;

        @Override
        public String toString() {
            switch(this) {
            case GAME_UPCOMING:
                return "Upcoming game alert";
            case UNKNOWN:
            default:
                return "Unknown";
            }
        }
    }

    public NotificationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationType nt = (NotificationType)intent.getSerializableExtra("notificationType");
        Log.d(TAG, "Notification type: " + nt);
        BarNotification.showScheduleNotification(this);
    }
}

