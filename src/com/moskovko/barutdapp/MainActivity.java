package com.moskovko.barutdapp;

//import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.animation.TranslateAnimation;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ArrayAdapter;

public class MainActivity extends ActionBarActivity
{
    private static final String TAG = "MainActivity";
    private static boolean DEBUG = true;

    private View mMainView;
    private View mDrawerView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.barutdlogo);
        iv.setLayoutParams(new AbsoluteLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0, 0));
        AbsoluteLayout al = new AbsoluteLayout(this);
        al.setLayoutParams(new DrawerLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        al.addView(iv);
        this.mMainView = iv;

        String[] menu = { "Schedule", "Roster", "Standings" };
        ListView lv = new ListView(this);
        lv.setBackgroundColor(0xFFFF0000);
        lv.setLayoutParams(new DrawerLayout.LayoutParams(
            600, LayoutParams.MATCH_PARENT, Gravity.START));
        lv.setAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, menu)); 
        this.mDrawerView = lv;

        DrawerLayout dl = new DrawerLayout(this);
        dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.START);
        dl.setLayoutParams(new LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.FILL_PARENT));
        dl.setDrawerListener(new DrawerListener() {
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
        dl.addView(al);
        dl.addView(lv);

        setContentView(dl);

        /*
         *setContentView(R.layout.main);
         */
    }

    private void slideMainView(float offset) {
        int width = mDrawerView.getLayoutParams().width;
        float delta = (width * offset);

        if (DEBUG) Log.d(TAG, "slideMainView: width: " + width + " delta: " + delta);

        AbsoluteLayout.LayoutParams lp = (AbsoluteLayout.LayoutParams)mMainView
            .getLayoutParams();
        lp.x = (int)delta;
        mMainView.setLayoutParams(lp);
    }
}
