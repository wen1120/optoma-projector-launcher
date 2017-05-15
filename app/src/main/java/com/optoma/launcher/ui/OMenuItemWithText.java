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

        final MarginLayoutParams layoutParams = new MarginLayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );

        value = new TextView(context);
        value.setLayoutParams(layoutParams);
        value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        value.setTextColor(OUtil.getColor(context, R.color.menu_text));
        addViewAtRight(value);
    }

    public void setValue(String s) {
        value.setText(s);
    }

}
