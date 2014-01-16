package com.moskovko.barutdapp;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.view.ViewGroup.MarginLayoutParams;
import android.content.Context;
import android.view.Gravity;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.lang.StringBuilder;

public class GameSnapshotView extends BoxView {
    private static final String TAG = "GameSnapshotView";

    private static final int DATE_FONT_SIZE = 12;
    private static final int TEAM_FONT_SIZE = 18;

    public GameSnapshotView(Context context, String home,
                String away, Date date)
    {
        super(context);

        TextView homeView = new TextView(context);
        homeView.setText(home);
        homeView.setTextColor(getResources()
            .getColor(R.color.snapshot_team_name));
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
            300, LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        homeView.setLayoutParams(rlp); 
        homeView.setTextSize(TEAM_FONT_SIZE);
        homeView.setId(R.id.snapshot_home_team);

        TextView awayView = new TextView(context);
        awayView.setText(away);
        awayView.setTextColor(getResources()
            .getColor(R.color.snapshot_team_name));
        rlp = new RelativeLayout.LayoutParams(
            300, LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        awayView.setLayoutParams(rlp);
        awayView.setTextSize(TEAM_FONT_SIZE);
        awayView.setGravity(Gravity.RIGHT);
        awayView.setId(R.id.snapshot_away_team);

        TextView dateView = new TextView(context);
        dateView.setText(getDateString(date));
        dateView.setTextColor(getResources()
            .getColor(R.color.snapshot_date));
        rlp = new RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        dateView.setLayoutParams(rlp);
        dateView.setTextSize(DATE_FONT_SIZE);
        dateView.setGravity(Gravity.CENTER_HORIZONTAL);

        this.addView(homeView);
        this.addView(awayView);
        this.addView(dateView);
    }

    private String getDateString(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        StringBuilder sb = new StringBuilder();
        sb.append(c.get(Calendar.MONTH));
        sb.append(" ");       
        sb.append(c.get(Calendar.DATE));
        sb.append(", ");       
        sb.append(c.get(Calendar.YEAR));
        sb.append("\n");       
        sb.append(c.get(Calendar.HOUR));
        sb.append(":");       
        sb.append(c.get(Calendar.MINUTE));
        sb.append(c.get(Calendar.AM_PM));
        return sb.toString();
    }
}

