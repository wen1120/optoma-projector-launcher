package com.optoma.launcher.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ken.chou on 5/12/2017.
 */

public class ODemo extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);

        final TextView tv = new TextView(this);
        tv.setText("hello, world");
        root.addView(tv);

        final OSeekBar seekBar = new OSeekBar(this);
//        seekBar.setLayoutParams(new ViewGroup.LayoutParams(
//                OUtil.dpToPixel(this, 512), ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(seekBar);

        final OPicker picker = new OPicker(this);
        picker.addOption("Apple");
        picker.addOption("Banana");
        picker.addOption("Cherry");
        root.addView(picker);

        final OMenuItem menuItem = new OMenuItem(this);
        menuItem.setText("apple");
        root.addView(menuItem);

        root.setPadding(
                OUtil.dpToPixel(this, 32),
                OUtil.dpToPixel(this, 32),
                OUtil.dpToPixel(this, 32),
                OUtil.dpToPixel(this, 32));
        setContentView(root);
    }
}
