package com.moskovko.barutdapp;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.content.Context;
import android.view.Gravity;

public class GameStatsSnapshotView extends GameSnapshotView {
    private static final int SCORE_FONT_SIZE = 70;

    private TextView mHomeScore;
    private TextView mAwayScore;

    public GameStatsSnapshotView(Context context) {
        super(context);

        mHomeScore = new TextView(context);
        mHomeScore.setText("17");
        mHomeScore.setTextColor(getResources()
            .getColor(R.color.snapshot_score));
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.BELOW, R.id.snapshot_home_team);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        mHomeScore.setLayoutParams(rlp);
        mHomeScore.setTextSize(SCORE_FONT_SIZE);
        mHomeScore.setGravity(Gravity.CENTER_HORIZONTAL);

        mAwayScore = new TextView(context);
        mAwayScore.setText("17");
        mAwayScore.setTextColor(getResources()
            .getColor(R.color.snapshot_score));
        rlp = new RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.BELOW, R.id.snapshot_away_team);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mAwayScore.setLayoutParams(rlp);
        mAwayScore.setTextSize(SCORE_FONT_SIZE);
        mAwayScore.setGravity(Gravity.CENTER_HORIZONTAL);

        this.addView(mHomeScore);
        this.addView(mAwayScore);
    }
}

