package com.optoma.launcher.ui;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.optoma.launcher.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuController implements ViewController {
    private View view;

    @BindView(R.id.selector)
    ViewGroup itemsWrapper;

    @BindView(R.id.content)
    ViewGroup contentWrapper;

    private MenuController from;
    private List<ViewController> items = new ArrayList<>();

    public MenuController(Context context, @LayoutRes int layout, MenuController from) {
        view = View.inflate(context, layout, null);

        ButterKnife.bind(this, view);

        this.from = from;
    }

    public void addItem(ViewController item) {
        final View v = item.getView();

        v.setId(View.generateViewId());

        if(items.isEmpty()) {
            v.setNextFocusUpId(v.getId()); //TODO: how about horizontal menus?
            v.setNextFocusDownId(v.getId());
            v.requestFocus();
        } else {
            final View first = items.get(0).getView();
            first.setNextFocusUpId(v.getId());

            final View oldLast = items.get(items.size()-1).getView();
            oldLast.setNextFocusDownId(View.NO_ID);

            v.setNextFocusDownId(items.get(0).getView().getId());
        }

        items.add(item);
        itemsWrapper.addView(v);

    }

    public void addDummyItem(View v) {
        itemsWrapper.addView(v);
    }

    public void setContent(@Nullable View content, ViewController trigger) {
        if(trigger != null) {
            trigger.getView().setSelected(true);
        }
        contentWrapper.removeAllViews();
        if(content != null) {
            contentWrapper.addView(content);
        }
    }

    public ViewController getSelectedItem() {
        for(ViewController c: items) {
            if(c.getView().isSelected()) return c;
        }
        throw new RuntimeException();
    }

    public void dismiss() {
        if(from != null) {
            from.setContent(null, null);
            final View selectedItem = from.getSelectedItem().getView();
            selectedItem.setSelected(false);
            selectedItem.requestFocus();
        } else {
            throw new RuntimeException("dismissing root menu");
        }
    }

    @Override
    public View getView() {
        return view;
    }
}
