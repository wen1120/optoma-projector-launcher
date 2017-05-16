package com.optoma.launcher.ui;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.optoma.launcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuGroupController implements ViewController {
    private View view;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.content)
    LinearLayout content;

    public MenuGroupController(Context context, String title) {
        view = View.inflate(context, R.layout.menu_group, null);
        ButterKnife.bind(this, view);

        this.title.setText(title);
    }

    @Override
    public View getView() {
        return view;
    }

    public void addItem(View v) {
        content.addView(v);
    }
}
