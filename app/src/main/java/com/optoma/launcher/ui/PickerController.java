package com.optoma.launcher.ui;

import android.content.Context;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.TextView;

import com.optoma.launcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickerController implements ViewController {

    private View view;
    private String[] values;
    private int currentIndex;

    @BindView(R.id.title) TextView title;
    @BindView(R.id.current) TextView current;
    @BindView(R.id.left_arrow) View leftButton;
    @BindView(R.id.right_arrow) View rightButton;

    public PickerController(Context context, String title, String[] values, int initial) {
        view = View.inflate(context, R.layout.menu_picker, null);
        this.values = values;
        this.currentIndex = initial;
        ButterKnife.bind(this, view);

        this.title.setText(title);
        this.current.setText(values[initial]);
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
