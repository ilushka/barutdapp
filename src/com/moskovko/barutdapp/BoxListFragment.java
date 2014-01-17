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

public abstract class BoxListFragment extends Fragment {
    private ProgressBar mProgressSpinner;
    private ScrollView mScrollView;

    public BoxListFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState)
    {
        mScrollView = new ScrollView(getActivity());
        mScrollView.setLayoutParams(new LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.FILL_PARENT));
        mScrollView.setBackgroundResource(R.color.main_background);
        // So that its contents gets stretched out to fullscreen:
        mScrollView.setFillViewport(true);
        mScrollView.setVerticalScrollBarEnabled(false);

        return mScrollView;
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
        while (v != null) {
            LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT);
            lp.setMargins(20, 20, 20, 0);
            v.setLayoutParams(lp);
            ll.addView(v);
            index++;
            v = getViewForBox(index);
        }
    }

    protected void showProgressSpinner(boolean doShow) {
        if (doShow) {
            mScrollView.removeAllViews();

            RelativeLayout rl = new RelativeLayout(getActivity());
            rl.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
            mScrollView.addView(rl);

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        
            mProgressSpinner = new ProgressBar(getActivity());
            mProgressSpinner.setIndeterminate(true);
            mProgressSpinner.setLayoutParams(lp);
            rl.addView(mProgressSpinner);

            mProgressSpinner.setProgress(0);
        } else {
            if (mProgressSpinner != null) {
                mProgressSpinner.setProgress(100);
                mScrollView.removeAllViews();
                mProgressSpinner = null;
            }
        }
    }


    abstract public View getViewForBox(int index);
}
