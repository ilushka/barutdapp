package com.moskovko.barutdapp;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.AdapterView;
import android.widget.TextView;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.app.Fragment;
import java.util.HashMap;
import java.lang.Runnable;
import android.os.Build;
import android.support.v4.app.ActionBarDrawerToggle;
import android.content.res.Configuration;
import android.view.MenuItem;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import java.io.IOException;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";

    private static final String MENU_SCHEDULE = "Schedule";
    private static final String MENU_ROSTER = "Roster";
    private static final String MENU_HISTORY = "Results";
    private static final String SENDER_ID = "332315534460";
    private static final String PREFERENCE_REGID = "registration_id";
    private static final String PREFERENCE_APPVER = "application_version";

    private static boolean DEBUG = true;

    private View mFragmentContainer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private HashMap<String, Runnable> menuItems;
    private GoogleCloudMessaging mGCM;
    private String mRegistrationID;

    private class DrawerArrayAdapter extends ArrayAdapter {
        public DrawerArrayAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView v = (TextView)super.getView(position, convertView, parent);
            v.setTextColor(getResources().getColor(R.color.drawer_text));
            v.setShadowLayer(2.0f, 2.0f, 2.0f, 0xFF000000);
            return v;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            getActionBar().setHomeButtonEnabled(true);
        }

        initDrawerMenuItems();

        // Container for each "section", which slides along with drawer:
        this.mFragmentContainer = new AbsoluteLayout(this);
        this.mFragmentContainer.setLayoutParams(new AbsoluteLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0, 0));
        this.mFragmentContainer.setId(R.id.fragment_container);

        // Container for the mFragmentContainer:
        AbsoluteLayout container = new AbsoluteLayout(this);
        container.setLayoutParams(new DrawerLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        container.setBackgroundResource(R.color.main_background);
        container.addView(this.mFragmentContainer);

        // Drawer view:
        ListView drawerListView = new ListView(this);
        drawerListView.setBackgroundResource(R.color.drawer_background);
        drawerListView.setLayoutParams(new DrawerLayout.LayoutParams(
            getResources().getInteger(R.integer.drawer_width),
                LayoutParams.MATCH_PARENT, Gravity.START));
        drawerListView.setAdapter(new DrawerArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            menuItems.keySet().toArray(new String[0]))); 
        drawerListView.setDivider(new ColorDrawable(getResources()
            .getColor(R.color.drawer_divider)));
        drawerListView.setDividerHeight(getResources()
            .getInteger(R.integer.drawer_divider_height));
        drawerListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent,
                View view, int position, long id)
            {
                MainActivity.this.mDrawerLayout.closeDrawer(Gravity.START);
                String t = ((TextView)view).getText().toString();
                ((Runnable)menuItems.get(t)).run();
            }
        });

        // The main layout:
        this.mDrawerLayout = new DrawerLayout(this);
        this.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.START);
        this.mDrawerLayout.setLayoutParams(new LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.FILL_PARENT));
        this.mDrawerLayout.addView(container);
        this.mDrawerLayout.addView(drawerListView);

        // Drawer toggle feature:
        this.mDrawerToggle = new ActionBarDrawerToggle(this, this.mDrawerLayout,
                R.drawable.ic_drawer, R.string.open_drawer, R.string.close_drawer)
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (DEBUG) Log.d(TAG, "onDrawerSlide: slideOffset: " + slideOffset);
                super.onDrawerSlide(drawerView, slideOffset);
                slideFragmentContainer(slideOffset);
            }

        };
        this.mDrawerLayout.setDrawerListener(this.mDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // GCM:
        this.mGCM = GoogleCloudMessaging.getInstance(this);
        this.mRegistrationID = getRegistrationId(getApplicationContext());
        if (mRegistrationID.isEmpty()) {
            registerInBackground(getApplicationContext());
        } else {
            Log.i(TAG, "GCM registration ID: " + this.mRegistrationID);
        }

        setContentView(this.mDrawerLayout);
        showFirstFragment(new ScheduleFragment());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        this.mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (this.mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    private void slideFragmentContainer(float offset) {
        int width = getResources().getInteger(R.integer.drawer_divider_height);
        float delta = (width * offset);

        if (DEBUG) Log.d(TAG, "slideFragmentContainer: width: " + width + " delta: " + delta);

        AbsoluteLayout.LayoutParams lp = (AbsoluteLayout.LayoutParams)mFragmentContainer
            .getLayoutParams();
        lp.x = (int)delta;
        mFragmentContainer.setLayoutParams(lp);
    }

    private void showFirstFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, fragment);
        ft.commit();
    }

    private void showScheduleFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ScheduleFragment sf = new ScheduleFragment();
        ft.replace(R.id.fragment_container, sf);
        ft.commit();
    }

    private void showHistoryFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        HistoryFragment gsf = new HistoryFragment();
        ft.replace(R.id.fragment_container, gsf);
        ft.commit();
    }

    private void showRosterFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        RosterFragment rf = new RosterFragment();
        ft.replace(R.id.fragment_container, rf);
        ft.commit();
    }

    private void initDrawerMenuItems() {
        menuItems = new HashMap<String, Runnable>();
        menuItems.put(MENU_SCHEDULE, new Runnable() {
            @Override
            public void run() {
                showScheduleFragment();
            }
        });
        menuItems.put(MENU_ROSTER, new Runnable() {
            @Override
            public void run() {
                showRosterFragment();
            }
        });
        menuItems.put(MENU_HISTORY, new Runnable() {
            @Override
            public void run() {
                showHistoryFragment();
            }
        });
    }

    /** http://developer.android.com/google/gcm/client.html#sample-register */

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationID = prefs.getString(PREFERENCE_REGID, "");
        if (registrationID.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PREFERENCE_APPVER, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationID;
    }

    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // Should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private void registerInBackground(final Context context) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (mGCM == null) {
                        mGCM = GoogleCloudMessaging.getInstance(context);
                    }
                    mRegistrationID = mGCM.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + mRegistrationID;

                    // You should send the registration ID to your server over HTTP,
                    // so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device
                    // will send upstream messages to a server that echo back the
                    // message using the 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context, mRegistrationID);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
            }
        }.execute(null, null, null);
    }

    private void sendRegistrationIdToBackend() {
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFERENCE_REGID, regId);
        editor.putInt(PREFERENCE_APPVER, appVersion);
        editor.commit();
    }
}
