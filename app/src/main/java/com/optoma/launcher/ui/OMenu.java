package com.optoma.launcher.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.optoma.launcher.R;

public class OMenu extends LinearLayout {

    private OMenuItemWithToggle title = null;
    private LinearLayout content = null;
    private OnFocusChangeListener backgroundStyle = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            setSelected(hasFocus);
        }
    };

    public OMenu(Context context) {
        super(context);

        setOrientation(LinearLayout.VERTICAL);
        setBackgroundResource(R.drawable.menu_bg);

        title = createTitle();
        title.setOnFocusChangeListener(backgroundStyle);
        addView(title);

        content = createContent();

        final RelativeLayout contentWrapper = new RelativeLayout(context);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        contentWrapper.setLayoutParams(layoutParams);

        contentWrapper.addView(content);
//        contentWrapper.addView(createVerticalBar());

        addView(contentWrapper);

        title.setChecked(false);
    }

    private OMenuItemWithToggle createTitle() {
        final OMenuItemWithToggle title = new OMenuItemWithToggle(getContext());

        final ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, OUtil.dpToPixel(getContext(), 48));

        title.setLayoutParams(layoutParams);

        title.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                for(int i=0; i<content.getChildCount(); i++) {
                    content.getChildAt(i).setEnabled(isChecked);
                    content.getChildAt(i).setFocusable(isChecked);
                }
                // content.setEnabled(isChecked);
            }
        });

        return title;
    }

    private View createVerticalBar() {
        final View bar = new View(getContext());

        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                OUtil.dpToPixel(getContext(), 2), OUtil.dpToPixel(getContext(), 0));

        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        layoutParams.setMarginStart(OUtil.dpToPixel(getContext(), 29));

        bar.setLayoutParams(layoutParams);

        bar.setBackgroundColor(OUtil.getColor(getContext(), R.color.secondary_light_gray));

        return bar;
    }

    private LinearLayout createContent() {
        final LinearLayout container = new LinearLayout(getContext());
        container.setOrientation(LinearLayout.VERTICAL);

        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // layoutParams.setMarginStart(OUtil.dpToPixel(context, 24));
        container.setLayoutParams(layoutParams);
        
        return container;
    }

    public void setTitle(String s) {
        title.setText(s);
    }

    public void addItem(View v) {
        v.setOnFocusChangeListener(backgroundStyle);
        content.addView(v);
    }

    public OMenuItemWithToggle getTitle() {
        return title;
    }

    public LinearLayout getContent() {
        return content;
    }

    public void setOnCheckedChangeListener(final CompoundButton.OnCheckedChangeListener listener) {
        final CompoundButton.OnCheckedChangeListener old = title.getOnCheckedChangeListener();
        if(old == null) {
            title.setOnCheckedChangeListener(listener);
        } else {
            title.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    old.onCheckedChanged(buttonView, isChecked);
                    listener.onCheckedChanged(buttonView, isChecked);
                }
            });
        }
    }

}
