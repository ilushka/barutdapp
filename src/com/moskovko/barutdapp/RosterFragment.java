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
import android.widget.ImageView;

public class RosterFragment extends BaseFragment {
    static private final String TAG = "RosterFragment";
    static private final boolean DEBUG = true; 

    private PlayerParser.Player[] mPlayers;

    public RosterFragment() {
        super();
    }

    private TextView createTextCell() {
        Resources r = getActivity().getResources();
        TextView cell = new TextView(getActivity());
        cell.setLayoutParams(new TableRow.LayoutParams(
            LayoutParams.WRAP_CONTENT, 80));
        cell.setTextColor(0xFFFFFFFF);
        cell.setTextSize(r.getInteger(R.integer.main_font_size));
        cell.setGravity(Gravity.CENTER_VERTICAL);
        cell.setPadding(20, 0, 20, 0);
        return cell;
    }

    private ImageView createImageCell() {
        ImageView cell = new ImageView(getActivity());
        cell.setLayoutParams(new TableRow.LayoutParams(
            LayoutParams.WRAP_CONTENT, 80));
        return cell;
    }

    private View getViewForCell(int column, int row) {
        if (row >= mPlayers.length) {
            return null;
        }
        
        if (DEBUG) {
            Log.d(TAG, "getViewCell: column: " + column +
                " row: " + row);
        }

        View cell;
        PlayerParser.Player player = mPlayers[row];
        switch (column) {
        case 0: { // Number
            TextView tv = createTextCell();
            if (row == 0) {
                tv.setTextColor(0xFFFFFF00);
                tv.setText("#"); 
            } else {
                tv.setText(Integer.toString(player.number)); 
            }
            cell = (View)tv;
        }
        break;
        case 1: { // Position:
            TextView tv = createTextCell();
            if (row == 0) {
                tv.setTextColor(0xFFFFFF00);
                tv.setText("POS");
            } else {
                tv.setText(player.position.toString()); 
            }
            cell = (View)tv;
        }
        break;
        case 2: { // Name:
            TextView tv = createTextCell();
            if (row == 0) {
                TableRow.LayoutParams trlp =
                    new TableRow.LayoutParams(
                        LayoutParams.WRAP_CONTENT, 80);
                trlp.span = 2;
                tv.setLayoutParams(trlp);
                tv.setTextColor(0xFFFFFF00);
                tv.setText("NAME"); 
            } else {
                tv.setText(player.name); 
            }
            cell = (View)tv;
        }
        break;
        case 3: { // Country icon (doesn't have header)
            ImageView iv = createImageCell();
            iv.setImageResource(getCountryFlagIcon(player.country));
            cell = (View)iv;
        }
        break;
        default:
            cell = null;
            break;
        }

        return cell;
    }

    private void populateFragment() {
        mScrollView.removeAllViews();

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
                xx += ((TableRow.LayoutParams)cell.getLayoutParams()).span;
                cell = getViewForCell(xx, yy);
            } while (cell != null);

            tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
            tl.addView(divider);

            yy++;
            xx = 0;
            cell = getViewForCell(xx, yy);
        }

        mScrollView.addView(tl);
    }

    private int getCountryFlagIcon(String country) {
        Resources r = getActivity().getResources();
        return r.getIdentifier(country.toLowerCase(), "drawable",
            "com.moskovko.barutdapp");
    }    

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
                    mPlayers = new PlayerParser(response).parse();
                    RosterFragment.this.showProgressSpinner(false);
                    if ((mPlayers == null) || (mPlayers.length == 0)) {
                        RosterFragment.this.showSingleMessage("Error");
                    } else {
                        RosterFragment.this.populateFragment();
                    }
                }
            }).execute(new URL("http://stormy-citadel-3700.herokuapp.com/roster.xml"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

