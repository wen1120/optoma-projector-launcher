package com.optoma.launcher.ui;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.optoma.launcher.R;

public class OMenuItemWithText extends OMenuItem {
    private TextView value = null;

    public OMenuItemWithText(Context context) {
        super(context);

        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMarginEnd(OUtil.dpToPixel(context, 24));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

        value = new TextView(context);
        value.setLayoutParams(layoutParams);
        value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        value.setTextColor(OUtil.getColor(context, R.color.menu_text));
        addView(value);
    }

    public void setValue(String s) {
        value.setText(s);
    }

}

/*
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:background="@drawable/menu_item_bg"
    android:focusable="true"
    android:focusableInTouchMode="true"
    android:clickable="true">

    <TextView
        android:id="@+id/menu_item_label_text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentLeft="true"
        android:layout_centerVertical="true"
        android:textColor="#D0D3D4"
        android:textSize="18sp"
        android:layout_marginLeft="24dp"/>

    <TextView
        android:id="@+id/menu_item_label_label"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_marginRight="24dp"
        android:layout_centerVertical="true"
        android:textColor="#71C5E8"
        android:textSize="18sp"
        android:focusable="false"
        android:focusableInTouchMode="false"
        android:clickable="false"
        />

</RelativeLayout>
 */