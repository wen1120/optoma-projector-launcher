package com.optoma.launcher.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.optoma.launcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuGroupWithToggleController implements ViewController {

    private View view;

    private boolean isCheckedInitially;

    @BindView(R.id.title)
    ViewGroup title;

    @BindView(R.id.tabs)
    LinearLayout content;

    public MenuGroupWithToggleController(final Context context, String title, boolean isChecked) {
        view = View.inflate(context, R.layout.menu_group_with_toggle, null);
        ButterKnife.bind(this, view);

        isCheckedInitially = isChecked;

        final ToggleController toggleController = new ToggleController(context, title, isChecked);
        this.title.addView(toggleController.getView());

        toggleController.addOnFocusChangeListeners(focusListener);

        toggleController.addOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for(int i=0; i<content.getChildCount(); i++) {
                    final View c = content.getChildAt(i);
                    // c.setEnabled(isChecked);
                    c.setFocusable(isChecked);
                    c.setFocusableInTouchMode(isChecked);
                    c.setClickable(isChecked);

                }

            }
        });
    }

    @Override
    public View getView() {
        return view;
    }

    public void addItem(View v) {
        v.setOnFocusChangeListener(focusListener); // TODO
        // v.setEnabled(isCheckedInitially);
        v.setFocusable(isCheckedInitially);
        content.addView(v);
    }

    private View.OnFocusChangeListener focusListener = new View.OnFocusChangeListener() {
        private int focusCount = 0;

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus) {
                focusCount++;
            } else {
                focusCount--;
            }
            view.setBackground((focusCount>0) ?
                    view.getResources().getDrawable(R.drawable.menu_bg_select) :
                    view.getResources().getDrawable(R.drawable.menu_bg_normal));
        }
    };
}
