package com.optoma.launcher.ui;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.BaseInputConnection;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.optoma.launcher.R;

public class OMenuItemWithPicker extends OMenuItem {

    private OMenuItem menuItem;
    private OPicker picker;

    public OMenuItemWithPicker(Context context) {
        super(context);

        picker = new OPicker(context);
        picker.setFocusable(false);
        picker.setFocusableInTouchMode(false);
        picker.setClickable(false);

        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        lp.weight = 1;
        picker.setLayoutParams(lp);
        addViewAtBottom(picker);
        final BaseInputConnection conn = new BaseInputConnection(picker, true);

        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() != KeyEvent.ACTION_DOWN) return false;

                if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    picker.next();
                } else if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    picker.previous();
                }

                return false;
            }
        });
    }


    public void addOption(String s) {
        picker.addOption(s);
    }
}
