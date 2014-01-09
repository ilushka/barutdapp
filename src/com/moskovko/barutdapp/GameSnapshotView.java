package com.moskovko.barutdapp;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.view.ViewGroup.MarginLayoutParams;
import android.content.Context;
import android.view.Gravity;

public class GameSnapshotView extends BoxView {
    private static final String TAG = "GameSnapshotView";

    private static final int DATE_FONT_SIZE = 12;
    private static final int TEAM_FONT_SIZE = 18;

    private TextView mHomeTeam;
    private TextView mAwayTeam;

    public GameSnapshotView(Context context) {
        super(context);

        mHomeTeam = new TextView(context);
        mHomeTeam.setText("FC Bar United");
        mHomeTeam.setTextColor(getResources()
            .getColor(R.color.snapshot_team_name));
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
            300, LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        mHomeTeam.setLayoutParams(rlp); 
        mHomeTeam.setTextSize(TEAM_FONT_SIZE);
        mHomeTeam.setId(R.id.snapshot_home_team);

        mAwayTeam = new TextView(context);
        mAwayTeam.setText("Injured Reserves");
        mAwayTeam.setTextColor(getResources()
            .getColor(R.color.snapshot_team_name));
        rlp = new RelativeLayout.LayoutParams(
            300, LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mAwayTeam.setLayoutParams(rlp);
        mAwayTeam.setTextSize(TEAM_FONT_SIZE);
        mAwayTeam.setGravity(Gravity.RIGHT);
        mAwayTeam.setId(R.id.snapshot_away_team);

        TextView date = new TextView(context);
        date.setText("Feb 14, 2014\n10:30PM");
        date.setTextColor(getResources()
            .getColor(R.color.snapshot_date));
        rlp = new RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        date.setLayoutParams(rlp);
        date.setTextSize(DATE_FONT_SIZE);
        date.setGravity(Gravity.CENTER_HORIZONTAL);

        this.addView(mHomeTeam);
        this.addView(mAwayTeam);
        this.addView(date);
    }
}

