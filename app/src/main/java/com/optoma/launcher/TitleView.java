package com.optoma.launcher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleView extends RelativeLayout {

    private RelativeLayout layout;
    private View view;
    private final Context context;
    private Typeface typeface;
    private TextView tvTime, tvDate;

    private final Handler timeHandle = new Handler();

    private final Runnable timeRun = new Runnable() {

        public void run() {
            setTvTimeText(TimeUtil.getTime());
            setTvDateDate(TimeUtil.getDate());
            timeHandle.postDelayed(this, 1000);
        }

    };

    public TitleView(Context context) {
        super(context);
        this.context = context;
        initTitleView();
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initTitleView();
    }

    public void initTitleView() {
        view = LayoutInflater.from(context).inflate(R.layout.titleview, this, true);
        layout = (RelativeLayout) view.findViewById(R.id.home_title);
        tvTime = (TextView) view.findViewById(R.id.title_bar_hour);
        tvDate = (TextView) view.findViewById(R.id.title_bar_date);
        typeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/Roboto-Regular.ttf");
        tvTime.setTypeface(typeface);
        tvDate.setTypeface(typeface);
        timeHandle.post(timeRun);
    }

    public void setTvTimeText(String text) {
        tvTime.setText(text);
    }

    public void setTvDateDate(String text) {
        tvDate.setText(text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
