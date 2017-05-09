package com.optoma.launcher.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.optoma.launcher.R;

public class ToggleMenuItem extends MenuItem {
    boolean isOn;

    public ToggleMenuItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ToggleMenuItem,
                0, 0);

        try {
            text = a.getString(R.styleable.ToggleMenuItem_text);
            isOn = a.getBoolean(R.styleable.ToggleMenuItem_value, false);
        } finally {
            a.recycle();
        }

        final View view = inflate(getContext(), R.layout.toggle_menu_item, null);
        addView(view);

        final TextView tv = (TextView) view.findViewById(R.id.toggle_menu_item_text);
        tv.setText(text);

        final ImageView iv = (ImageView) view.findViewById(R.id.toggle_menu_item_value);
        iv.setSelected(isOn);

        view.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() != KeyEvent.ACTION_DOWN) return false;
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    Log.d("ken", "toggle: "+isOn);
                    isOn = !isOn;
                    iv.setSelected(isOn);
                }
                return false;
            }
        });

    }
}