package com.moskovko.barutdapp;

import android.support.v4.content.WakefulBroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.ComponentName;
import android.app.Activity;

/** http://developer.android.com/google/gcm/client.html#sample-receive */

public class GcmReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
