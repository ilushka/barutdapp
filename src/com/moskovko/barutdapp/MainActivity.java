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
//import android.view.Window;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.AdapterView;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";
    private static boolean DEBUG = true;
    private static int DRAWER_WIDTH = 600;
    private static int FRAGMENT_CONTAINER_ID = 666;

    private View mFragmentContainer;
    private DrawerLayout mDrawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.mFragmentContainer = new AbsoluteLayout(this);
        this.mFragmentContainer.setLayoutParams(new AbsoluteLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0, 0));
        this.mFragmentContainer.setId(FRAGMENT_CONTAINER_ID);

        AbsoluteLayout container = new AbsoluteLayout(this);
        container.setLayoutParams(new DrawerLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        container.setBackgroundResource(R.color.main_background);
        container.addView(this.mFragmentContainer);

        String[] menu = { "Schedule", "Roster", "Standings" };
        ListView drawerListView = new ListView(this);
        drawerListView.setBackgroundResource(R.color.drawer_background);
        drawerListView.setLayoutParams(new DrawerLayout.LayoutParams(
            DRAWER_WIDTH, LayoutParams.MATCH_PARENT, Gravity.START));
        drawerListView.setAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, menu)); 
        drawerListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent,
                View view, int position, long id)
            {
                MainActivity.this.mDrawerLayout.closeDrawer(Gravity.START);
                showRosterFragment();
            }
        });

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

        showScheduleFragment();
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

    private void showScheduleFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ScheduleFragment sf = new ScheduleFragment();
        ft.add(FRAGMENT_CONTAINER_ID, sf);
        ft.commit();
    }

    private void showRosterFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        RosterFragment rf = new RosterFragment();
        ft.replace(FRAGMENT_CONTAINER_ID, rf);
        ft.commit();
    }
}
