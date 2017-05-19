package com.optoma.launcher;

import android.content.Context;
import android.util.DisplayMetrics;

public class Util {

    public static int dp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
