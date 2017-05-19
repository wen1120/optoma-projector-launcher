package com.optoma.launcher.ui;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.optoma.launcher.R;
import com.optoma.launcher.Shortcut;
import com.optoma.launcher.SizeChanger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShortcutController implements ViewController {
    private View view;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.label) TextView label;

    public ShortcutController(Context context, @DrawableRes int img, String lab) {
        view = new Shortcut(context); // View.inflate(context, R.layout.shortcut, null);
        ButterKnife.bind(this, view);

        image.setImageResource(img);
        label.setText(lab);

    }

    public void setOnClickListener(View.OnClickListener lis) {
        view.setOnClickListener(lis);
    }

    @Override
    public View getView() {
        return view;
    }

}
