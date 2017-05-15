package com.optoma.launcher.ui;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.optoma.launcher.R;

public class OMenuItem extends LinearLayout {

    private TextView text = null;

    private LinearLayout upper = null;

    public OMenuItem(Context context) {
        super(context);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setClickable(true);
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundResource(R.drawable.menu_item_bg);
        setGravity(Gravity.CENTER);
        setPadding(OUtil.dpToPixel(context, 24), 0, OUtil.dpToPixel(context, 24), 0);

        upper = new LinearLayout(context);
        upper.setOrientation(LinearLayout.HORIZONTAL);
        upper.setGravity(Gravity.CENTER);
        final LinearLayout.LayoutParams upperLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        upperLp.weight = 1;
        upper.setLayoutParams(upperLp);

        text = createText();
        upper.addView(text);

        final Space innerSpace = new Space(context);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                1, 1
        );
        layoutParams.weight = 1;
        innerSpace.setLayoutParams(layoutParams);

        upper.addView(innerSpace);
        addView(upper);
    }

    private TextView createText() {
        final TextView t = new TextView(getContext());
        final MarginLayoutParams layoutParams = new MarginLayoutParams(
                MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT
        );

        t.setLayoutParams(layoutParams);

        t.setTextColor(OUtil.getColor(getContext(), R.color.menu_text));
        t.setTextSize(18);
        return t;
    }


    public void setIndent(boolean hasIndent) {
        if(hasIndent) {
            setPadding(OUtil.dpToPixel(getContext(), 48), 0, OUtil.dpToPixel(getContext(), 24), 0);
        } else {
            setPadding(OUtil.dpToPixel(getContext(), 24), 0, OUtil.dpToPixel(getContext(), 24), 0);
        }
    }

    public void setText(String s) {
        text.setText(s);
    }

    public void addViewAtRight(View v) { upper.addView(v); }
    public void addViewAtBottom(View v) { addView(v); }
}
