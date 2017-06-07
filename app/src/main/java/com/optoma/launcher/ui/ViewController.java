package com.optoma.launcher.ui;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ViewController {
    protected View view;
    protected List<View.OnFocusChangeListener> onFocusChangeListeners = new ArrayList<>();

    public ViewController(View view) {
        this.view = view;
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                for(View.OnFocusChangeListener l: onFocusChangeListeners) {
                    l.onFocusChange(v, hasFocus);
                }
            }
        });
    }

    public View getView() { return null; }

    public void setEnabled(boolean isEnabled) {}

    public void addOnFocusChangeListener(View.OnFocusChangeListener lis) {
        onFocusChangeListeners.add(lis);
    }
}
