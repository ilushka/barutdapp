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

        int xx = 0;
        int yy = 0;
        View cell = getViewForCell(xx, yy);
        while (cell != null) {
            View divider = new View(getActivity());
            divider.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.FILL_PARENT, 6));
            //divider.setBackgroundColor(0xFF000000);

            TableRow tr = new TableRow(getActivity());
            tr.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
            if (yy == 0) {
                tr.setBackgroundColor(0xFF380000);
            } else {
                tr.setBackgroundResource(R.color.box_background);
            }

            do {
                tr.addView(cell);
                xx++;
                cell = getViewForCell(xx, yy);
            } while (cell != null);

            tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
            tl.addView(divider);

            yy++;
            xx = 0;
            Log.d(TAG, "MONKEY: yy: " + yy);
            cell = getViewForCell(xx, yy);
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

    private View getViewForCell(int column, int row) {
        Log.d(TAG, "MONKEY: row: " + row);
        int textColor = 0xFFFFFFFF;
        if (row == 0) {
            textColor = 0xFFFFFF00;
        }

        // TODO: Hardcoding 10 rows for now:
        if (row == 10)
            return null;

        Resources r = getActivity().getResources();
        TextView cell = new TextView(getActivity());
        cell.setLayoutParams(new TableRow.LayoutParams(
            LayoutParams.WRAP_CONTENT, 80));
        cell.setTextColor(textColor);
        cell.setTextSize(r.getInteger(R.integer.main_font_size));
        cell.setGravity(Gravity.CENTER_VERTICAL);
        cell.setPadding(20, 0, 20, 0);

        switch (column) {
        case 0:
            if (row == 0) {
                cell.setText("#"); 
            } else {
                cell.setText("31"); 
            }
            break;
        case 1:
            if (row == 0) {
                cell.setText("POS");
            } else {
                cell.setText("D-G"); 
            }
            break;
        case 2:
            if (row == 0) {
                cell.setText("NAME"); 
            } else {
                cell.setText("M. Udrea Spenea"); 
            }
            break;
        default:
            return null;
        }

        return cell;
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

