package com.moskovko.barutdapp;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.content.Context;
import android.view.Gravity;

public class GameStatsSnapshotView extends BoxView {
    private static final int SCORE_FONT_SIZE = 40;
    private static final int DATE_FONT_SIZE = 12;
    private static final int TEAM_FONT_SIZE = 18;

    public GameStatsSnapshotView(Context context) {
        super(context);

        TextView homeTeam = new TextView(context);
        homeTeam.setText("FC Bar United");
        homeTeam.setTextColor(getResources()
            .getColor(R.color.snapshot_team_name));
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
            300, LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        homeTeam.setLayoutParams(rlp); 
        homeTeam.setTextSize(TEAM_FONT_SIZE);
        homeTeam.setId(R.id.snapshot_home_team);

        TextView awayTeam = new TextView(context);
        awayTeam.setText("Injured Reserves");
        awayTeam.setTextColor(getResources()
            .getColor(R.color.snapshot_team_name));
        rlp = new RelativeLayout.LayoutParams(
            300, LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        awayTeam.setLayoutParams(rlp);
        awayTeam.setTextSize(TEAM_FONT_SIZE);
        awayTeam.setGravity(Gravity.RIGHT);
        awayTeam.setId(R.id.snapshot_away_team);

        TextView score = new TextView(context);
        score.setText("17:5");
        score.setTextColor(getResources()
            .getColor(R.color.snapshot_score));
        rlp = new RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        score.setLayoutParams(rlp);
        score.setTextSize(SCORE_FONT_SIZE);
        score.setGravity(Gravity.CENTER_HORIZONTAL);
        score.setIncludeFontPadding(false);
        score.setPaddingRelative(0, 0, 0, 0);
        score.setPadding(0, 0, 0, 0);
        score.setId(R.id.snapshot_score);

        TextView date = new TextView(context);
        date.setText("Feb 14, 2014, 10:30PM");
        date.setTextColor(getResources()
            .getColor(R.color.snapshot_date));
        rlp = new RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.BELOW, R.id.snapshot_score);
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        date.setLayoutParams(rlp);
        date.setTextSize(DATE_FONT_SIZE);
        date.setGravity(Gravity.CENTER_HORIZONTAL);

        this.addView(homeTeam);
        this.addView(awayTeam);
        this.addView(score);
        this.addView(date);
    }
}

