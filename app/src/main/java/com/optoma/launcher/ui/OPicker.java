package com.optoma.launcher.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.optoma.launcher.R;

import java.util.ArrayList;
import java.util.List;

public class OPicker extends RelativeLayout {
    public static int HORIZONTAL = 0;
    public static int VERTICAL = 1;
    private int position = HORIZONTAL;
    private List<String> options = new ArrayList<>();
    private int selectedOption = 0;
    private TextView currentTextView = null;

    public OPicker(Context context) {
        super(context);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setClickable(true);
        setBackgroundResource(R.drawable.menu_item_bg);

        addView(getLeftArrow());

        currentTextView = getCurrent();
        addView(currentTextView);

        addView(getRightArrow());

        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() != KeyEvent.ACTION_DOWN) return false;

                if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    next();
                } else if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    previous();
                }

                return false;
            }
        });

    }

    public void next() {
        if(selectedOption < options.size()-1) {
            selectedOption++;
        }
        if(selectedOption >= 0 && selectedOption < options.size()) {
            currentTextView.setText(options.get(selectedOption));
        }
    }

    public void previous() {
        if(selectedOption > 0) {
            selectedOption--;
        }
        if(selectedOption >= 0 && selectedOption < options.size()) {
            currentTextView.setText(options.get(selectedOption));
        }
    }

    private TextView getCurrent() {
        final TextView current = new TextView(getContext());

        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        current.setLayoutParams(layoutParams);
        current.setTextSize(18);
        current.setTextColor(OUtil.getColor(getContext(), R.color.menu_text));

        return current;
    }

    private ImageView getLeftArrow() {
        final ImageView arrow = new ImageView(getContext());
        arrow.setImageResource(R.drawable.left_arrow427);
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        arrow.setLayoutParams(layoutParams);
        return arrow;
    }

    private ImageView getRightArrow() {
        final ImageView arrow = new ImageView(getContext());
        arrow.setImageResource(R.drawable.right_arrow427);
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        arrow.setLayoutParams(layoutParams);
        return arrow;
    }

    public void addOption(String o) {
        options.add(o);
        if(options.size() > selectedOption) {
            currentTextView.setText(options.get(selectedOption));
        }
    }


}
