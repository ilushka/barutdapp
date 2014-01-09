package com.moskovko.barutdapp;

import android.util.Log;
import android.view.View;

public class GameStatsFragment extends BoxListFragment {
    public GameStatsFragment() {
        super();
    }

    @Override
    public View getViewForBox(int index) {
        return new GameStatsSnapshotView(getActivity());
    }
}

