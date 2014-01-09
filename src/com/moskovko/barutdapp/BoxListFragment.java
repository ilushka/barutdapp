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

public abstract class BoxListFragment extends Fragment {
    public BoxListFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState)
    {
        LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(new LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.FILL_PARENT));
        populateLayout(ll);

        ScrollView sv = new ScrollView(getActivity());
        sv.setLayoutParams(new LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.FILL_PARENT));
        sv.setBackgroundResource(R.color.main_background);
        sv.setVerticalScrollBarEnabled(false);
        sv.addView(ll);

        return sv;
    }

    private void populateLayout(LinearLayout layout) {
        for (int ii = 0; ii < 15; ii++) {
            View v = getViewForBox(ii);
            LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT);
            lp.setMargins(20, 20, 20, 0);
            v.setLayoutParams(lp);
            layout.addView(v);
        }
    }

    abstract public View getViewForBox(int index);
}
