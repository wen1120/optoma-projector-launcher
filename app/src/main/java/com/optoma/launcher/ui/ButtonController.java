package com.optoma.launcher.ui;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.optoma.launcher.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ButtonController extends ViewController {

    @BindView(R.id.title) TextView title;
    @BindView(R.id.info) TextView info;
    @BindView(R.id.leading_image) ImageView leadingImage;
    @BindView(R.id.trailing_image) ImageView trailingImage;
    View.OnClickListener onClickListener;

    public ButtonController(Context context,
                            String title) {
        this(context, title, null);
    }
    public ButtonController(Context context,
                            String title,
                            String info) {
        this(context, title, info, -1, -1);
    }
    public ButtonController(Context context,
                            String title,
                            @Nullable String info,
                            @DrawableRes int leadingImage,
                            @DrawableRes int trailingImage) {
        super(View.inflate(context, R.layout.menu_button, null));
        ButterKnife.bind(this, view);

        this.title.setText(title);
        if(leadingImage>=0)
            this.leadingImage.setImageResource(leadingImage);
        if(trailingImage>=0)
            this.trailingImage.setImageResource(trailingImage);

        if(info != null) {
            this.info.setText(info);
        } else {
            this.info.setVisibility(View.GONE);
        }

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() != KeyEvent.ACTION_DOWN) return false;
                if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    return true;
                }
                return false;
            }
        });
    }

    public void setOnClickListener(final View.OnClickListener lis) {
        view.setOnClickListener(lis);
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        view.setEnabled(isEnabled);
        view.setFocusable(isEnabled);
        view.setFocusableInTouchMode(isEnabled);
        view.setClickable(isEnabled);

        title.setEnabled(isEnabled);
        info.setEnabled(isEnabled);
    }
}
