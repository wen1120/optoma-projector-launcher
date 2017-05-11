package com.optoma.launcher.ui;

import android.content.Context;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.optoma.launcher.R;

public class OMenuItem extends RelativeLayout {
    private TextView text = null;

    public OMenuItem(Context context) {
        super(context);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setClickable(true);

        setBackgroundResource(R.drawable.menu_item_bg);

        text = createText();
        addView(text);
    }

    private TextView createText() {
        final TextView t = new TextView(getContext());

        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMarginStart(OUtil.dpToPixel(getContext(), 24));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        t.setLayoutParams(layoutParams);

        t.setTextColor(OUtil.getColor(getContext(), R.color.menu_text));
        t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        return t;
    }


    public void setIndent(int indentInSp) {
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMarginStart(OUtil.dpToPixel(getContext(), 24+indentInSp));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        text.setLayoutParams(layoutParams);
    }

    public void setText(String s) {
        text.setText(s);
    }


}
