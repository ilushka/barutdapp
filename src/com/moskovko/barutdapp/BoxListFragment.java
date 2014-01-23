package com.moskovko.barutdapp;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.widget.ScrollView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.content.res.Resources;
import android.widget.TextView;

public abstract class BoxListFragment extends FragmentWithSpinner {
    public BoxListFragment() {
        super();
    }

    protected void populateFragment() {
        mScrollView.removeAllViews();

        LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(new LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.FILL_PARENT));
        mScrollView.addView(ll);

        int index = 0;
        View v = getViewForBox(index);
        Resources r = getActivity().getResources();
        while (v != null) {
            LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT);
            lp.setMargins(r.getInteger(R.integer.box_margin_left),
                r.getInteger(R.integer.box_margin_top),
                r.getInteger(R.integer.box_margin_right),
                r.getInteger(R.integer.box_margin_bottom));
            v.setLayoutParams(lp);
            ll.addView(v);
            index++;
            v = getViewForBox(index);
        }
    }

    protected void showSingleMessage(String message) {
        mScrollView.removeAllViews();

        RelativeLayout rl = new RelativeLayout(getActivity());
        rl.setLayoutParams(new LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.FILL_PARENT));
        mScrollView.addView(rl);
        
        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rllp.addRule(RelativeLayout.CENTER_IN_PARENT);
        TextView tv = new TextView(getActivity());
        tv.setLayoutParams(rllp);
        tv.setText(message);
        tv.setTextColor(0xFFFFFFFF);
        tv.setTextSize(18);
        rl.addView(tv);
    }

    abstract public View getViewForBox(int index);
}
