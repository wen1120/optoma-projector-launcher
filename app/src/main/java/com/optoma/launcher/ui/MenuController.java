package com.optoma.launcher.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.optoma.launcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuController implements ViewController {
    private View view;

    @BindView(R.id.content)
    LinearLayout content;

    @BindView(R.id.parent)
    ViewGroup parent;

    @BindView(R.id.child)
    ViewGroup child;

    @BindView(R.id.parent_child_gap)
    View gap;

    public MenuController(Context context, int weight, int totalWeight) {
        view = View.inflate(context, R.layout.menu_panel, null);

        ButterKnife.bind(this, view);

        final LinearLayout.LayoutParams parentLp = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        parentLp.weight = weight;
        parent.setLayoutParams(parentLp);

        final LinearLayout.LayoutParams childLp = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        childLp.weight = totalWeight - weight;
        child.setLayoutParams(childLp);


        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() != KeyEvent.ACTION_DOWN) return false;
                Log.d("ken", "key on parent");
                return false;
            }
        });

    }

    public void addItem(View v) {
        content.addView(v);
    }

    public void setChild(@Nullable View v) {
        child.removeAllViews();
        if(v != null) {
            child.addView(v);
            gap.setVisibility(View.VISIBLE);
        } else {
            gap.setVisibility(View.GONE);
        }
    }

    @Override
    public View getView() {
        return view;
    }
}
