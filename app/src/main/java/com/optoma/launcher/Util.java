package com.optoma.launcher;

import android.content.Context;
import android.util.DisplayMetrics;

public class Util {

    public static int dp(Context context, int px) {
        // from https://developer.android.com/guide/practices/screens_support.html#dips-pels
        return (int) (px * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
