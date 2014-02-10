package com.moskovko.barutdapp;

import android.app.IntentService;
import android.content.Intent;

public class NotificationService extends IntentService {
    private static final String TAG = "NotificationService";

    public NotificationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        BarNotification.showScheduleNotification(this);
    }
}

