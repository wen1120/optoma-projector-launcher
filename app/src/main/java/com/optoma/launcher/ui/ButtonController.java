package com.optoma.launcher.ui;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.optoma.launcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ButtonController implements ViewController {
    private View view;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.leading_image) ImageView leadingImage;
    @BindView(R.id.trailing_image) ImageView trailingImage;
    View.OnClickListener onClickListener;

    public ButtonController(Context context,
                            String title) {
        this(context, title, -1, -1);
    }
    public ButtonController(Context context,
                            String title,
                            @DrawableRes int leadingImage,
                            @DrawableRes int trailingImage) {
        view = View.inflate(context, R.layout.menu_button, null);
        ButterKnife.bind(this, view);

        this.title.setText(title);
        if(leadingImage>=0)
            this.leadingImage.setImageResource(leadingImage);
        if(trailingImage>=0)
            this.trailingImage.setImageResource(trailingImage);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ken", "in on click");

                if(onClickListener != null) {
                    Log.d("ken", "lis is not null");
                    onClickListener.onClick(v);
                }
            }
        });

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

    public void setOnClickListener(View.OnClickListener lis) {
       onClickListener = lis;
    }

    @Override
    public View getView() {
        return view;
    }
}
