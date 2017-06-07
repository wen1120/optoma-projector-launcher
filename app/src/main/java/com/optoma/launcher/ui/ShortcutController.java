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

public class ShortcutController extends ViewController {
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.label) TextView label;

    public ShortcutController(Context context, @DrawableRes int img, String lab) {
        // super(new Shortcut(context));
        super(View.inflate(context, R.layout.shortcut, null));

        view.setBackgroundResource(R.drawable.setup_shortcut_bg);

        ButterKnife.bind(this, view);

        image.setImageResource(img);
        label.setText(lab);

    }

    public void setOnClickListener(View.OnClickListener lis) {
        view.setOnClickListener(lis);
    }

}
