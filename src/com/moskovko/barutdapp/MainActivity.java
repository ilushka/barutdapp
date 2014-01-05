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

    private View mMainView;
    private DrawerLayout mDrawerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.mMainView = new AbsoluteLayout(this);
        this.mMainView.setLayoutParams(new AbsoluteLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0, 0));
        this.mMainView.setId(666);
        AbsoluteLayout al = new AbsoluteLayout(this);
        al.setLayoutParams(new DrawerLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        al.addView(this.mMainView);

        String[] menu = { "Schedule", "Roster", "Standings" };
        ListView lv = new ListView(this);
        lv.setBackgroundColor(0xFFFF0000);
        lv.setLayoutParams(new DrawerLayout.LayoutParams(
            DRAWER_WIDTH, LayoutParams.MATCH_PARENT, Gravity.START));
        lv.setAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, menu)); 
        lv.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent,
                View view, int position, long id)
            {
                MainActivity.this.mDrawerView.closeDrawer(Gravity.START);
                showRosterFragment();
            }
        });

        this.mDrawerView = new DrawerLayout(this);
        this.mDrawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.START);
        this.mDrawerView.setLayoutParams(new LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.FILL_PARENT));
        this.mDrawerView.setDrawerListener(new DrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (DEBUG) Log.d(TAG, "onDrawerSlide: slideOffset: " + slideOffset);
                slideMainView(slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        this.mDrawerView.addView(al);
        this.mDrawerView.addView(lv);

        setContentView(this.mDrawerView);

        showScheduleFragment();
    }

    private void slideMainView(float offset) {
        int width = DRAWER_WIDTH;
        float delta = (width * offset);

        if (DEBUG) Log.d(TAG, "slideMainView: width: " + width + " delta: " + delta);

        AbsoluteLayout.LayoutParams lp = (AbsoluteLayout.LayoutParams)mMainView
            .getLayoutParams();
        lp.x = (int)delta;
        mMainView.setLayoutParams(lp);
    }

    private void showScheduleFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ScheduleFragment sf = new ScheduleFragment();
        ft.add(666, sf);
        ft.commit();
    }

    private void showRosterFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        RosterFragment rf = new RosterFragment();
        ft.replace(666, rf);
        ft.commit();
    }
}
