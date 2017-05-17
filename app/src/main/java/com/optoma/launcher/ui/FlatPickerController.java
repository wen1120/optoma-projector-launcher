package com.optoma.launcher.ui;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.optoma.launcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ken.chou on 5/15/2017.
 */

public class FlatPickerController implements ViewController {

    private View view;
    private int[] choices;
    private int currentIndex;

    @BindView(R.id.title) TextView title;
    @BindView(R.id.tabs) GridLayout content;

    public FlatPickerController(Context context, String title, @DrawableRes int[] choices, int initial) {
        view = View.inflate(context, R.layout.menu_flat_picker, null);
        this.choices = choices;
        this.currentIndex = initial;
        ButterKnife.bind(this, view);

        this.title.setText(title);
    }

    @Override
    public View getView() {
        return view;
    }
}
