package com.moskovko.barutdapp;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.content.Context;
import android.view.Gravity;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.lang.StringBuilder;

public class HistorySnapshotView extends BoxView {
    private static final int SCORE_FONT_SIZE = 40;
    private static final int DATE_FONT_SIZE = 12;
    private static final int TEAM_FONT_SIZE = 18;

    public HistorySnapshotView(Context context, String home,
                String away, int homeScore, int awayScore,
                Date date)
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

        TextView scoreView = new TextView(context);
        scoreView.setText(getScoreString(homeScore, awayScore));
        scoreView.setTextColor(getResources()
            .getColor(R.color.snapshot_score));
        rlp = new RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        scoreView.setLayoutParams(rlp);
        scoreView.setTextSize(SCORE_FONT_SIZE);
        scoreView.setGravity(Gravity.CENTER_HORIZONTAL);
        scoreView.setIncludeFontPadding(false);
        scoreView.setPaddingRelative(0, 0, 0, 0);
        scoreView.setPadding(0, 0, 0, 0);
        scoreView.setId(R.id.snapshot_score);

        TextView dateView = new TextView(context);
        dateView.setText(getDateString(date));
        dateView.setTextColor(getResources()
            .getColor(R.color.snapshot_date));
        rlp = new RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.BELOW, R.id.snapshot_score);
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        dateView.setLayoutParams(rlp);
        dateView.setTextSize(DATE_FONT_SIZE);
        dateView.setGravity(Gravity.CENTER_HORIZONTAL);

        this.addView(homeView);
        this.addView(awayView);
        this.addView(scoreView);
        this.addView(dateView);
    }

    private String getDateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy, hh:mmaa");
        return sdf.format(date);
    }

    private String getScoreString(int home, int away) {
        StringBuilder sb = new StringBuilder();
        sb.append(home);
        sb.append(':');
        sb.append(away);
        return sb.toString();
    }
}

