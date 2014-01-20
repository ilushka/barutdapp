package com.moskovko.barutdapp;

//import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
//import android.view.Window;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
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
import java.util.Map;
import java.lang.Runnable;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";

    private static final String MENU_SCHEDULE = "Schedule";
    private static final String MENU_ROSTER = "Roster";
    private static final String MENU_HISTORY = "Results";

    private static boolean DEBUG = true;
    private static int DRAWER_WIDTH = 600;
    private static int FRAGMENT_CONTAINER_ID = 666;
    private static int DRAWER_DIVIDER_HEIGHT = 3;
    private HashMap<String, Runnable> menuItems;
    
    private View mFragmentContainer;
    private DrawerLayout mDrawerLayout;

    private class DrawerArrayAdapter extends ArrayAdapter {
        public DrawerArrayAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView v = (TextView)super.getView(position, convertView, parent);
            v.setTextColor(getResources().getColor(R.color.drawer_text));
            return v;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        initMenuItems();

        // Container for each "section", which slides along with drawer:
        this.mFragmentContainer = new AbsoluteLayout(this);
        this.mFragmentContainer.setLayoutParams(new AbsoluteLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0, 0));
        this.mFragmentContainer.setId(FRAGMENT_CONTAINER_ID);

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
            DRAWER_WIDTH, LayoutParams.MATCH_PARENT, Gravity.START));
        drawerListView.setAdapter(new DrawerArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            menuItems.keySet().toArray(new String[0]))); 
        drawerListView.setDivider(new ColorDrawable(getResources()
            .getColor(R.color.drawer_divider)));
        drawerListView.setDividerHeight(DRAWER_DIVIDER_HEIGHT);
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
        this.mDrawerLayout.setDrawerListener(new DrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (DEBUG) Log.d(TAG, "onDrawerSlide: slideOffset: " + slideOffset);
                slideFragmentContainer(slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        this.mDrawerLayout.addView(container);
        this.mDrawerLayout.addView(drawerListView);

        setContentView(this.mDrawerLayout);

        showFirstFragment(new ScheduleFragment());
    }

    private void slideFragmentContainer(float offset) {
        int width = DRAWER_WIDTH;
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
        ft.add(FRAGMENT_CONTAINER_ID, fragment);
        ft.commit();
    }

    private void showScheduleFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ScheduleFragment sf = new ScheduleFragment();
        ft.replace(FRAGMENT_CONTAINER_ID, sf);
        ft.commit();
    }

    private void showHistoryFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        HistoryFragment gsf = new HistoryFragment();
        ft.replace(FRAGMENT_CONTAINER_ID, gsf);
        ft.commit();
    }

    private void showRosterFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        RosterFragment rf = new RosterFragment();
        ft.replace(FRAGMENT_CONTAINER_ID, rf);
        ft.commit();
    }

    private void initMenuItems() {
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
