package com.optoma.launcher;

import android.view.View;

public class FocusComponent {
    private View view;

    public FocusComponent(View v) {
        this.view = v;
    }

    public View getFocusedView() {
        return view;
    }

    public void focusUp() {}
    public void focusDown() {}
    public void focusLeft() {}
    public void focusRight() {}
}
