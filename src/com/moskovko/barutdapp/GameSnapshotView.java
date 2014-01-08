package com.moskovko.barutdapp;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.view.ViewGroup.MarginLayoutParams;
import android.content.Context;
import android.view.Gravity;

public class GameSnapshotView extends RelativeLayout {
    private static final String TAG         = "GameSnapshotView";

    private static final int DATE_FONT_SIZE = 12;
    private static final int TEAM_FONT_SIZE = 18;

    private TextView mHomeTeam;
    private TextView mAwayTeam;

    public GameSnapshotView(Context context) {
        super(context);

        LayoutParams lp = new LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(lp);
        this.setPadding(20, 10, 20, 10);
        this.setBackgroundResource(R.color.box_background);

        mHomeTeam = new TextView(context);
        mHomeTeam.setText("FC Bar United");
        mHomeTeam.setTextColor(getResources()
            .getColor(R.color.snapshot_team_name));
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
            300, LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        mHomeTeam.setLayoutParams(rlp); 
        mHomeTeam.setTextSize(TEAM_FONT_SIZE);

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

        /*
         *TextView vs = new TextView(context);
         *vs.setText("VS");
         *vs.setTextColor(getResources().getColor(R.color.snapshot_vs));
         *rlp = new RelativeLayout.LayoutParams(
         *    LayoutParams.WRAP_CONTENT,
         *    LayoutParams.WRAP_CONTENT);
         *rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
         *vs.setLayoutParams(rlp);
         *vs.setTextSize(90);
         */

        TextView date = new TextView(context);
        date.setText("Feb 14, 2014\n10:30PM");
        date.setTextColor(getResources().getColor(R.color.snapshot_vs));
        rlp = new RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        date.setLayoutParams(rlp);
        date.setTextSize(DATE_FONT_SIZE);
        date.setGravity(Gravity.CENTER_HORIZONTAL);

        /*
         *this.addView(vs);
         */
        this.addView(mHomeTeam);
        this.addView(mAwayTeam);
        this.addView(date);
    }
}

