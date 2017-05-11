package com.optoma.launcher.ui;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

public class OMenuItemWithToggle extends OMenuItem {

    private OToggle toggle = null;

    public OMenuItemWithToggle(Context context) {
        super(context);

        toggle = createToggle();
        addView(toggle);

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


    private OToggle createToggle() {
        final OToggle t = new OToggle(getContext());

        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                OUtil.dpToPixel(getContext(), 32), OUtil.dpToPixel(getContext(), 32)
        );
        layoutParams.setMarginEnd(OUtil.dpToPixel(getContext(), 24));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
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