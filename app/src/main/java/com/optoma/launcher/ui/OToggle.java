package com.optoma.launcher.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.optoma.launcher.R;


public class OToggle extends ToggleButton {

    private OnCheckedChangeListener onCheckedChangeListener;

    public OToggle(Context context) {
        super(context);
        setBackgroundResource(R.drawable.on_off);

        setFocusable(false);
        setFocusableInTouchMode(false);
        setClickable(false);

        setTextOn("");
        setTextOff("");
        setText("");
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        super.setOnCheckedChangeListener(listener);
        onCheckedChangeListener = listener;
    }

    public OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }
}
