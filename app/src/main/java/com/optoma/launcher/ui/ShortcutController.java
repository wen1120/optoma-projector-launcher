package com.optoma.launcher.ui;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.Log;
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

        ButterKnife.bind(this, view);

        view.setBackgroundResource(R.drawable.setup_shortcut_bg);

        view.setScaleX(0.8f);
        view.setScaleY(0.8f);
        view.setAlpha(0.7f);

        view.setOnFocusChangeListener(new SizeChanger(1, 0.8f, 80) {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                super.onFocusChange(v, hasFocus);
                view.setAlpha(hasFocus ? 1.0f : 0.7f);
            }
        });

        image.setImageResource(img);
        label.setText(lab);

    }

    public void setOnClickListener(View.OnClickListener lis) {
        view.setOnClickListener(lis);
    }

}
