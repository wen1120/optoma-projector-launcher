package com.optoma.launcher.ui;

import android.content.Context;
import android.inputmethodservice.Keyboard;
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

    }

    public void addItem(View v) {
        v.setId(View.generateViewId());
        final int childCount = content.getChildCount();

        if(childCount == 0) {
            v.setNextFocusUpId(v.getId());
            v.setNextFocusDownId(v.getId());
        } else {
            final View first = content.getChildAt(0);
            first.setNextFocusUpId(v.getId());

            final View oldLast = content.getChildAt(childCount-1);
            oldLast.setNextFocusDownId(View.NO_ID);
            
            v.setNextFocusDownId(content.getChildAt(0).getId());
        }

        content.addView(v);

    }

    public void setChild(@Nullable View v) {
        child.removeAllViews();
        if(v != null) {
            child.addView(v);
            gap.setVisibility(View.VISIBLE);
            v.requestFocus();
        } else {
            gap.setVisibility(View.GONE);
        }
    }

    @Override
    public View getView() {
        return view;
    }
}
