package com.moskovko.barutdapp;

import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.content.Context;

abstract public class BoxView extends RelativeLayout {
    public BoxView(Context context) {
        super(context);
        LayoutParams lp = new LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(lp);
        this.setPadding(20, 10, 20, 10);
        this.setBackgroundResource(R.color.box_background);
    }
}

