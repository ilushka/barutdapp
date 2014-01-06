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

public class ScheduleFragment extends Fragment {
    private static final String TAG = "ScheduleFragment";
    private static boolean DEBUG = true;

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState)
    {
        LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(new LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT));
        populateLayout(ll);

        ScrollView sv = new ScrollView(getActivity());
        sv.setLayoutParams(new LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT));
        sv.addView(ll);

        return sv;
    }

    private void populateLayout(LinearLayout layout) {
        for (int ii = 0; ii < 15; ii++) {
            View v = new View(getActivity());
            LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, 200);
            lp.setMargins(10, 10, 10, 10);
            v.setLayoutParams(lp);
            v.setBackgroundColor(0xFFFF00FF);
            layout.addView(v);
        }
    }
}

