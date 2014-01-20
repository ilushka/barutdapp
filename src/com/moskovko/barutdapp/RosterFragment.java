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
import android.content.res.Resources;
import android.view.Gravity;
import android.graphics.Typeface;

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
        Resources r = getActivity().getResources();

        TableLayout tl = new TableLayout(getActivity());
        tl.setLayoutParams(new TableLayout.LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.FILL_PARENT));
        tl.setPadding(r.getInteger(R.integer.box_margin_left),
            r.getInteger(R.integer.box_margin_top),
            r.getInteger(R.integer.box_margin_right),
            r.getInteger(R.integer.box_margin_bottom));

        for (int ii = 0; ii < 10; ++ii) {
            View divider = new View(getActivity());
            divider.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.FILL_PARENT, 6));
            //divider.setBackgroundColor(0xFF000000);
            
            TextView number, position, name;
            if (ii == 0) {
                number = new TextView(getActivity());
                number.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, 80));
                number.setTextColor(0xFFFF0000);
                number.setText("#");
                number.setTextSize(r.getInteger(R.integer.main_font_size));
                number.setGravity(Gravity.CENTER_VERTICAL);
                number.setPadding(20, 0, 20, 0);

                position = new TextView(getActivity());
                position.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, 80));
                position.setTextColor(0xFFFF0000);
                position.setText("POS");
                position.setTextSize(r.getInteger(R.integer.main_font_size));
                position.setGravity(Gravity.CENTER_VERTICAL);
                position.setPadding(20, 0, 20, 0);

                name = new TextView(getActivity());
                name.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, 80));
                name.setTextColor(0xFFFF0000);
                name.setText("NAME");
                name.setTextSize(r.getInteger(R.integer.main_font_size));
                name.setGravity(Gravity.CENTER_VERTICAL);
                name.setPadding(20, 0, 20, 0);
            } else {
                number = new TextView(getActivity());
                number.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, 80));
                number.setTextColor(0xFFFFFFFF);
                number.setText("31");
                number.setTextSize(r.getInteger(R.integer.main_font_size));
                number.setGravity(Gravity.CENTER_VERTICAL);
                number.setPadding(20, 0, 20, 0);

                position = new TextView(getActivity());
                position.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, 80));
                position.setTextColor(0xFFFFFFFF);
                position.setText("D-G");
                position.setTextSize(r.getInteger(R.integer.main_font_size));
                position.setGravity(Gravity.CENTER_VERTICAL);
                position.setPadding(20, 0, 20, 0);

                name = new TextView(getActivity());
                name.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, 80));
                name.setTextColor(0xFFFFFFFF);
                name.setText("M. Udrea Spenea");
                name.setTextSize(r.getInteger(R.integer.main_font_size));
                name.setGravity(Gravity.CENTER_VERTICAL);
                name.setPadding(20, 0, 20, 0);
            }

            TableRow.LayoutParams trlp = new TableRow.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT);
            TableRow tr = new TableRow(getActivity());
            tr.setLayoutParams(trlp);
            if (ii == 0) {
                tr.setBackgroundColor(0xFF380000);
            } else {
                tr.setBackgroundResource(R.color.box_background);
            }
            tr.addView(number);
            tr.addView(position);
            tr.addView(name);

            tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
            tl.addView(divider);
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

