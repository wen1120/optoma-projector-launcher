package com.optoma.launcher.ui;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.optoma.launcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ToggleController implements ViewController {
    private View view;

    @BindView(R.id.title) TextView title;
    @BindView(R.id.toggle)
    ToggleButton toggle;

    public ToggleController(Context context, String title, boolean isChecked) {
        view = View.inflate(context, R.layout.menu_toggle, null);
        ButterKnife.bind(this, view);

        this.title.setText(title);
        this.toggle.setChecked(isChecked);

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

    @Override
    public View getView() {
        return view;
    }
}
