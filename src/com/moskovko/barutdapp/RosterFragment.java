package com.moskovko.barutdapp;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ScrollView;
import java.net.URL;
import java.net.MalformedURLException;
import android.os.ResultReceiver;
import android.os.Handler;
import java.util.Date;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RosterFragment extends Fragment {
    static private final String TAG = "RosterFragment";

    private ScrollView mScrollView;

    public RosterFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState)
    {
        TableLayout tl = new TableLayout(getActivity());
        tl.setLayoutParams(new TableLayout.LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.FILL_PARENT));
        tl.setPadding(20, 20, 20, 10);
        for (int ii = 0; ii < 10; ++ii) {
          TextView name = new TextView(getActivity());
          name.setLayoutParams(new TableRow.LayoutParams(
              300, LayoutParams.WRAP_CONTENT));
          name.setTextColor(0xFFFFFFFF);
          name.setText("Marian Udrea Spenea");

          TableRow.LayoutParams trlp = new TableRow.LayoutParams(
              LayoutParams.FILL_PARENT,
              LayoutParams.WRAP_CONTENT);
          trlp.setMargins(0, 20, 0, 20);
          TableRow tr = new TableRow(getActivity());
          tr.setLayoutParams(trlp);
          tr.setBackgroundResource(R.color.box_background);
          tr.addView(name);

          tl.addView(tr, new TableLayout.LayoutParams(
              LayoutParams.FILL_PARENT,
              LayoutParams.WRAP_CONTENT));
        }

        mScrollView = new ScrollView(getActivity());
        mScrollView.setLayoutParams(new LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.FILL_PARENT));
        mScrollView.setBackgroundResource(R.color.main_background);
        // So that its contents gets stretched out to fullscreen:
        mScrollView.setFillViewport(true);
        mScrollView.setVerticalScrollBarEnabled(false);
        mScrollView.addView(tl);

        return mScrollView;
    }

/*
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
            return new ScheduleSnapshotView(getActivity(),
                mGames[index].homeTeamName,
                mGames[index].awayTeamName,
                mGames[index].date);
        }
        return null;
    }
*/
}

