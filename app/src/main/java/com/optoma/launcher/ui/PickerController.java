package com.optoma.launcher.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.TextView;

import com.optoma.launcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickerController extends ViewController {

    private String[] values;
    private int currentIndex;

    @BindView(R.id.title) TextView title;
    @BindView(R.id.current) TextView current;
    @BindView(R.id.left_arrow) View leftButton;
    @BindView(R.id.right_arrow) View rightButton;

    public PickerController(
            Context context, @Nullable String title, String[] values, int initial) {
        super(View.inflate(context, R.layout.menu_picker, null));
        this.values = values;
        this.currentIndex = initial;
        ButterKnife.bind(this, view);

        if(title == null) {
            this.title.setVisibility(View.GONE);
        } else {
            this.title.setText(title);
        }
        this.current.setText(values[initial]);

        view.setNextFocusUpId(view.getId());

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() != KeyEvent.ACTION_DOWN) return false;

                if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    onClickLeft();
                } else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    onClickRight();
                } else {
                    return false;
                }
                return true;
            }
        });
    }

    @OnClick(R.id.left_arrow) public void onClickLeft() {
        if(currentIndex > 0) currentIndex--;
        current.setText(values[currentIndex]);
    }

    @OnClick(R.id.right_arrow) public void onClickRight() {
        if(currentIndex < values.length-1) currentIndex++;
        current.setText(values[currentIndex]);
    }

    @Override
    public View getView() {
        return view;
    }
}
