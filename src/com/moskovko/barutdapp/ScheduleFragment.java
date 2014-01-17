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
import java.net.URL;
import java.net.MalformedURLException;
import android.os.ResultReceiver;
import android.os.Handler;
import java.util.Date;

public class ScheduleFragment extends BoxListFragment {
    static private final String TAG = "ScheduleFragment";

    private GameParser.Game[] mGames;

    public ScheduleFragment() {
        super();
    }

    @Override
    public void onStart() {
        super.onStart();

        this.showProgressSpinner(true);
        try {
            new GetGamesRequest(new ResultReceiver(new Handler()) {
                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    String response = resultData.getString(GetGamesRequest.REQUEST_RESPONSE);
                    Log.d(TAG, "onReceiveResult: response: " + response);
                    mGames = new GameParser(response).parse();
                    ScheduleFragment.this.showProgressSpinner(false);
                    ScheduleFragment.this.populateFragment();
                }
            }).execute(new URL("http://www.brrtr.com/schedule.xml"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getViewForBox(int index) {
        if (index < mGames.length) {
            return new GameSnapshotView(getActivity(),
                mGames[index].homeTeamName,
                mGames[index].awayTeamName,
                mGames[index].date);
        }
        return null;
    }
}

