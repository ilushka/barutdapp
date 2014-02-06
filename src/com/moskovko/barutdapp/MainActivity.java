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

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";

    private static final String MENU_SCHEDULE = "Schedule";
    private static final String MENU_ROSTER = "Roster";
    private static final String MENU_HISTORY = "Results";

    private static boolean DEBUG = true;
    private HashMap<String, Runnable> menuItems;
    
    private View mFragmentContainer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

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
}
