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
    private LinearLayout mFragmentLayout;

    public BoxListFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState)
    {
        mFragmentLayout = new LinearLayout(getActivity());
        mFragmentLayout.setOrientation(LinearLayout.VERTICAL);
        mFragmentLayout.setLayoutParams(new LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.FILL_PARENT));

        ScrollView sv = new ScrollView(getActivity());
        sv.setLayoutParams(new LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.FILL_PARENT));
        sv.setBackgroundResource(R.color.main_background);
        sv.setVerticalScrollBarEnabled(false);
        sv.addView(mFragmentLayout);

        return sv;
    }

    protected void populateFragment() {
        int index = 0;
        View v = getViewForBox(index);
        while (v != null) {
            LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT);
            lp.setMargins(20, 20, 20, 0);
            v.setLayoutParams(lp);
            mFragmentLayout.addView(v);
            index++;
            v = getViewForBox(index);
        }
    }

    abstract public View getViewForBox(int index);
}
