package com.optoma.launcher.ui;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;

public class OUtil {

    public static int dpToPixel(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int getColor(Context context, int id) {
        // context.getColor(id) requires API level 23
        return context.getResources().getColor(id);
    }

    public static Space getSpace(Context context, int widthInSp, int heightInSp) {
        final Space s = new Space(context);
        final int width = (widthInSp > 0) ? OUtil.dpToPixel(context, widthInSp) : widthInSp;
        final int height = (heightInSp > 0) ? OUtil.dpToPixel(context, heightInSp) : heightInSp;
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                width, height
        );
        s.setLayoutParams(layoutParams);
        return s;
    }

}
