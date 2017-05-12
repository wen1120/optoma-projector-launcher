package com.optoma.launcher.ui;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;

public class OMenuItemWithToggle extends OMenuItem {

    private OToggle toggle = null;

    public OMenuItemWithToggle(Context context) {
        super(context);

        toggle = getToggle();
        addViewAtRight(toggle);

        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() != KeyEvent.ACTION_DOWN) return false;
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    toggle.setChecked(!toggle.isChecked());

                }
                return false;
            }
        });
    }


    private OToggle getToggle() {
        final OToggle t = new OToggle(getContext());

        final MarginLayoutParams layoutParams = new MarginLayoutParams(
                OUtil.dpToPixel(getContext(), 32), OUtil.dpToPixel(getContext(), 32)
        );

        t.setLayoutParams(layoutParams);

        return t;
    }

    public void setChecked(boolean on) {
        toggle.setChecked(on);
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        toggle.setOnCheckedChangeListener(listener);
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return toggle.getOnCheckedChangeListener();
    }

}