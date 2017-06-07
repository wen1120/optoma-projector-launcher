package com.optoma.launcher;

import android.content.Context;
import android.util.DisplayMetrics;

public class Util {

    public static int dp(Context context, int px) {
        // from https://developer.android.com/guide/practices/screens_support.html#dips-pels
        // note that the accepted answer on stackoverflow is wrong:
        //   https://stackoverflow.com/questions/8309354/formula-px-to-dp-dp-to-px-android#
        return (int) (px * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
