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

        addChildren(root);

        root.setPadding(
                OUtil.dpToPixel(this, 32),
                OUtil.dpToPixel(this, 32),
                OUtil.dpToPixel(this, 32),
                OUtil.dpToPixel(this, 32));
        setContentView(root);
    }

    private void addChildren(ViewGroup root) {
//        final TextView tv = new TextView(this);
//        tv.setText("hello, world");
//        root.addView(tv);

        final ButtonController button = new ButtonController(this, "Click for More");
        root.addView(button.getView());

        final ToggleController toggle = new ToggleController(this, "Are you a genius?", false);
        root.addView(toggle.getView());

        final SeekBarController seekBar = new SeekBarController(this, "How's your mood?", 0, -100, 100, 5);
        root.addView(seekBar.getView());

        final PickerController picker = new PickerController(
                this, "What's your favorite fruit?", new String[] {"Apple", "Banana", "Cherry"}, 1);
        root.addView(picker.getView());

        final FlatPickerController flatPicker = new FlatPickerController(
                this, "Position", new int[] {}, 0);
        root.addView(flatPicker.getView());

    }
}
