package com.optoma.launcher.ui;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.optoma.launcher.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuGroupController extends ViewController {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tabs)
    LinearLayout content;

    List<ViewController> children = new ArrayList<>();

    public MenuGroupController(Context context, String title) {
        super(View.inflate(context, R.layout.menu_group, null));
        ButterKnife.bind(this, view);

        this.title.setText(title);
    }

    public void addItem(ViewController v) {
        v.addOnFocusChangeListener(focusListener);
        children.add(v);
        content.addView(v.getView());
    }

    private View.OnFocusChangeListener focusListener = new View.OnFocusChangeListener() {
        private int focusCount = 0;

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus) {
                focusCount++;
            } else {
                focusCount--;
            }
            view.setBackground((focusCount>0) ?
                    view.getResources().getDrawable(R.drawable.menu_bg_select) :
                    view.getResources().getDrawable(R.drawable.menu_bg_normal));
        }
    };
}
