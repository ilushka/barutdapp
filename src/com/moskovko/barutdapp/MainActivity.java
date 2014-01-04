package com.moskovko.barutdapp;

//import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.Window;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.view.Gravity;
import android.support.v4.widget.DrawerLayout.DrawerListener;

public class MainActivity extends ActionBarActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.barutdlogo);
        iv.setLayoutParams(new DrawerLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT));

/*
 *        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
 *            RelativeLayout.LayoutParams.FILL_PARENT,
 *            RelativeLayout.LayoutParams.FILL_PARENT);
 *
 *        RelativeLayout rl = new RelativeLayout(this);
 *        rl.addView(iv);
 *
 *        setContentView(rl);
 */
        DrawerLayout.LayoutParams dlp = new DrawerLayout.LayoutParams(
            600, LayoutParams.MATCH_PARENT);
        dlp.gravity = Gravity.START;

        View v = new View(this);
        v.setLayoutParams(dlp);
        v.setBackgroundColor(0xFFFF0000);

        DrawerLayout dl = new DrawerLayout(this);
        dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.START);
        dl.setLayoutParams(new LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.FILL_PARENT));
        dl.addView(iv);
        dl.addView(v);

        setContentView(dl);

        /*
         *setContentView(R.layout.main);
         */
    }
}
