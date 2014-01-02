package com.moskovko.barutdapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.Window;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.barutdlogo);

        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.FILL_PARENT,
            RelativeLayout.LayoutParams.FILL_PARENT);

        RelativeLayout rl = new RelativeLayout(this);
        rl.addView(iv);

        setContentView(rl);

        /*
         *setContentView(R.layout.main);
         */
    }
}
