package com.optoma.launcher.ui;

import android.content.Context;
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
    }

    @Override
    public View getView() {
        return view;
    }
}
