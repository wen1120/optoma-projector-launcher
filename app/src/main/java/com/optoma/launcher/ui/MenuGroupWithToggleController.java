package com.optoma.launcher.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.optoma.launcher.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuGroupWithToggleController extends ViewController {

    private boolean isCheckedInitially;

    @BindView(R.id.title)
    ViewGroup title;

    @BindView(R.id.tabs)
    LinearLayout content;

    @BindView(R.id.vertical_line) View verticalLine;

    List<ViewController> children = new ArrayList<>();

    public MenuGroupWithToggleController(final Context context, String title, boolean isChecked) {
        super(View.inflate(context, R.layout.menu_group_with_toggle, null));
        ButterKnife.bind(this, view);

        isCheckedInitially = isChecked;

        final ToggleController toggleController = new ToggleController(context, title, isChecked);
        this.title.addView(toggleController.getView());

        toggleController.addOnFocusChangeListener(focusListener);

        toggleController.addOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for(ViewController c: children) {
                    c.setEnabled(isChecked);
                }
                verticalLine.setEnabled(isChecked);
            }
        });
    }

    public void addItem(ViewController v) {
        v.addOnFocusChangeListener(focusListener); // TODO
        children.add(v);
        v.setEnabled(isCheckedInitially);

        content.addView(v.getView());
    }

    @Override
    public void setEnabled(boolean isEnabled) {

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
