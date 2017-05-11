package com.optoma.launcher.ui;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class OUtil {

    public static int dpToPixel(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int getColor(Context context, int id) {
        // context.getColor(id) requires API level 23
        return context.getResources().getColor(id);
    }


}
