package com.moskovko.barutdapp;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.content.Context;
import android.view.Gravity;

public class GameSnapshotView extends RelativeLayout {
    private static final String TAG         = "GameSnapshotView";

    private static final int DATE_FONT_SIZE = 12;
    private static final int TEAM_FONT_SIZE = 20;

    private TextView mOpponentName;

    public GameSnapshotView(Context context) {
        super(context);

        this.setPadding(20, 10, 20, 10);

        TextView barTextView = new TextView(context);
        barTextView.setText("FC Bar United");
        barTextView.setTextColor(getResources()
            .getColor(R.color.snapshot_team_name));
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
            300, LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        barTextView.setLayoutParams(rlp); 
        barTextView.setTextSize(TEAM_FONT_SIZE);
        // barTextView.setBackgroundColor(0xFF00FF00);

        mOpponentName = new TextView(context);
        mOpponentName.setText("Injured Reserves");
        mOpponentName.setTextColor(getResources()
            .getColor(R.color.snapshot_team_name));
        rlp = new RelativeLayout.LayoutParams(
            300, LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mOpponentName.setLayoutParams(rlp);
        mOpponentName.setTextSize(TEAM_FONT_SIZE);
        mOpponentName.setGravity(Gravity.RIGHT);
        // mOpponentName.setBackgroundColor(0xFF00FF00);

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
        this.addView(barTextView);
        this.addView(mOpponentName);
        this.addView(date);
    }
}

