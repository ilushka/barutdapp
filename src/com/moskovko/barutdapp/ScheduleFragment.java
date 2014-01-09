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

public class ScheduleFragment extends BoxListFragment {
    public ScheduleFragment() {
        super();
    }

    @Override
    public View getViewForBox(int index) {
        return new GameSnapshotView(getActivity());
    }
}

