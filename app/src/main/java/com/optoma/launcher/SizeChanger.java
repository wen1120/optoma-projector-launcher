package com.optoma.launcher;

import android.view.View;

/**
 * Created by ken.chou on 5/8/2017.
 */

public class SizeChanger implements View.OnFocusChangeListener {

    private float expandSize;
    private long duration;

    public SizeChanger(float expandSize, long duration) {
        this.expandSize = expandSize;
        this.duration = duration;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            v.animate().scaleX(expandSize).setDuration(duration);
            v.animate().scaleY(expandSize).setDuration(duration);
            v.setAlpha(1f);
        } else {
            v.animate().scaleX(0.8f).setDuration(duration);
            v.animate().scaleY(0.8f).setDuration(duration);
            v.setAlpha(0.7f);
        }
    }
}
