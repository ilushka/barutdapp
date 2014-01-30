package com.moskovko.barutdapp;

import android.util.Log;
import android.view.View;
import java.net.URL;
import java.net.MalformedURLException;
import android.os.ResultReceiver;
import android.os.Handler;
import android.os.Bundle;

public class HistoryFragment extends BoxListFragment {
    private static final String TAG = "HistoryFragment";

    private GameParser.Game[] mGames;

    public HistoryFragment() {
        super();
    }

    @Override
    public void onStart() {
        super.onStart();

        this.showProgressSpinner(true);
        try {
            new BaseHttpRequest(new ResultReceiver(new Handler()) {
                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    String response = resultData.getString(BaseHttpRequest.REQUEST_RESPONSE);
                    Log.d(TAG, "onReceiveResult: response: " + response);
                    mGames = new GameParser(response).parse();
                    HistoryFragment.this.showProgressSpinner(false);
                    if ((mGames == null) || (mGames.length == 0)) {
                        HistoryFragment.this.showSingleMessage("Error");
                    } else {
                        HistoryFragment.this.populateFragment();
                    }
                }
            }).execute(new URL("http://stormy-citadel-3700.herokuapp.com/results.xml"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getViewForBox(int index) {
        if (index < mGames.length) {
            return new HistorySnapshotView(getActivity(),
                mGames[index].homeTeamName,
                mGames[index].awayTeamName,
                mGames[index].homeScore,
                mGames[index].awayScore,
                mGames[index].date);
        }
        return null;
    }
}

