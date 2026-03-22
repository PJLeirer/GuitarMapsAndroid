package com.example.gmapandroid;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReferenceCard extends LinearLayout {

    private int cardId = 0;

    private String title = "Test Reference Card";
    private String info = "Test info text";

    private TextView titleView;
    private TextView infoView;




    public ReferenceCard(Context context) {
        super(context);

        //
        titleView = new TextView(context);
        titleView.setText(title);
        titleView.setTextSize(24);
        titleView.setPadding(10, 10, 10, 10);

        infoView = new TextView(context);
        infoView.setText(info);
        infoView.setTextSize(18);
        infoView.setPadding(10, 10, 10, 10);


        addView(titleView);
        addView(infoView);



    }


}
