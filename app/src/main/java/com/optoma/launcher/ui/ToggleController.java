package com.optoma.launcher.ui;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.optoma.launcher.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToggleController extends ViewController {

    @BindView(R.id.title) TextView title;
    @BindView(R.id.toggle)
    ToggleButton toggle;
    private List<CompoundButton.OnCheckedChangeListener> onCheckedChangeListeners = new ArrayList<>();

    public ToggleController(Context context, String title, boolean isChecked) {
        super(View.inflate(context, R.layout.menu_toggle, null));
        ButterKnife.bind(this, view);

        this.title.setText(title);
        this.toggle.setChecked(isChecked);

        this.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for(CompoundButton.OnCheckedChangeListener l: onCheckedChangeListeners) {
                    l.onCheckedChanged(buttonView, isChecked);
                }
            }
        });

        addOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ToggleController.this.title.setSelected(hasFocus);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle.setChecked(!toggle.isChecked());
            }
        });

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() != KeyEvent.ACTION_DOWN) return false;
                if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    view.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    public void addOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener lis) {
        onCheckedChangeListeners.add(lis);
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        view.setEnabled(isEnabled);
        view.setFocusable(isEnabled);
        view.setFocusableInTouchMode(isEnabled);
        view.setClickable(isEnabled);

        title.setEnabled(isEnabled);
        toggle.setEnabled(isEnabled);
    }

}
