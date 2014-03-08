package com.moskovko.barutdapp;

import android.util.Log;
import android.support.v4.app.NotificationCompat;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.content.Context;
import android.app.AlarmManager;
import java.util.Date;
import android.os.Bundle;

public class ScheduleNotification extends BaseNotification {
    private static final String TAG = "ScheduleNotification";

    private GameParser.Game mGame = null;

    public ScheduleNotification(Context context, GameParser.Game game) {
        super(context);
        mGame = game;
    }

    protected NotificationCompat.Builder getNotificationBuilder() {
        return new NotificationCompat.Builder(mContext)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle("Title")
            .setContentText("Text");
    }

    protected Bundle getNotificationArgs() {
        Bundle args = new Bundle();
        args.putSerializable(NotificationService.NOTIFICATION_ARGS_GAME, mGame);
        return args;
    }

    protected NotificationService.NotificationType getNotificationType() {
        return NotificationService.NotificationType.GAME_UPCOMING;
    }
}
