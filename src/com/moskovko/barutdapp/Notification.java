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

public class Notification {
    private static final String TAG = "Notification";

    public Notification() {
    }

    public static void showScheduleNotification(Context context, GameParser.Game game) {
        
        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle("My notification")
            .setContentText("Hello " + game.homeTeamName);

        getTimeDifference(game.date);

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

    private static String getTimeDifference(Date gameDate) {
        Calendar gameTime = Calendar.getInstance();
        gameTime.setTime(gameDate);
        long delta = System.currentTimeMillis() - gameTime.getTimeInMillis();
        Log.d(TAG, "MONKEY: getTimeDifference(): " + delta);
        return "";   
    }
}
