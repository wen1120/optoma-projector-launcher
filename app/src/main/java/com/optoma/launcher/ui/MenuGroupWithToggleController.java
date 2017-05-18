package com.optoma.launcher.ui;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.optoma.launcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuGroupWithToggleController implements ViewController {

    private View view;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tabs)
    LinearLayout content;

    @BindView(R.id.toggle)
    ToggleButton toggle;

    public MenuGroupWithToggleController(final Context context, String title, boolean isChecked) {
        view = View.inflate(context, R.layout.menu_group_with_toggle, null);
        ButterKnife.bind(this, view);

        this.title.setText(title);
        this.toggle.setChecked(isChecked);

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() != KeyEvent.ACTION_DOWN) return false;
                if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT ||
                        keyCode == KeyEvent.KEYCODE_DPAD_RIGHT ||
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    toggle.performClick();
                    return true;
                }
                return false;
            }
        });

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for(int i=0; i<content.getChildCount(); i++) {
                    final View c = content.getChildAt(i);
                    c.setEnabled(isChecked);
                    c.setClickable(isChecked);

                }
                if(content.getChildCount() > 0 && isChecked) {
                    view.setNextFocusDownId(content.getChildAt(0).getId());
                } else {
                    view.setNextFocusDownId(View.NO_ID);
                }
            }
        });
    }

    @Override
    public View getView() {
        return view;
    }

    public void addItem(View v) {
        v.setId(View.generateViewId());
        content.addView(v);
    }
}
