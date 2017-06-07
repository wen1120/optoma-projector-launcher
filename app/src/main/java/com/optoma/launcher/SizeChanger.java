package com.optoma.launcher;

import android.view.View;

/**
 * Created by ken.chou on 5/8/2017.
 */

public class SizeChanger implements View.OnFocusChangeListener {

    private float expandSize;
    private float shrinkSize;
    private long duration;

    public SizeChanger(float expandSize, long duration) {
        this(expandSize, 1, duration);
    }

    public SizeChanger(float expandSize, float shrinkSize, long duration) {
        this.expandSize = expandSize;
        this.shrinkSize = shrinkSize;
        this.duration = duration;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            v.animate().scaleX(expandSize).setDuration(duration);
            v.animate().scaleY(expandSize).setDuration(duration);
        } else {
            v.animate().scaleX(shrinkSize).setDuration(duration);
            v.animate().scaleY(shrinkSize).setDuration(duration);
        }
    }
}
