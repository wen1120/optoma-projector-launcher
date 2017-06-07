package com.optoma.launcher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.FrameLayout;

public class Shortcut extends FrameLayout {
    public Shortcut(Context context) {
        super(context);

        final View content = View.inflate(context, R.layout.shortcut, this);

        setBackgroundResource(R.drawable.home_shortcut_setup_normal);

        setClipChildren(false);
        setWillNotDraw(false);

        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setAlpha(0.7f);
        setScaleX(0.8f);
        setScaleY(0.8f);

        final SizeChanger sc = new SizeChanger(1f, 80);
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("focus-change", "hasFocus: "+hasFocus);
                Log.d("focus-change", "isSelected: "+isSelected());
                if(hasFocus || !isSelected()) {
                    sc.onFocusChange(v, hasFocus);
                }
                invalidate();
            }
        });
    }

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("onDraw", "hasFocus: "+hasFocus());
        Log.d("onDraw", "isSelected: "+isSelected());
        if(hasFocus() || isSelected()) {

            paint.setColor(Color.WHITE);
            canvas.drawLine(
                    Util.dp(getContext(), 10), Util.dp(getContext(), -13), Util.dp(getContext(), 10), 0, paint);

            final int radius = Util.dp(getContext(), 3);

            paint.setColor(getResources().getColor(R.color.secondary_yellow));

            canvas.drawOval(new RectF(Util.dp(getContext(), 10) - radius, Util.dp(getContext(), -13) - radius,
                    Util.dp(getContext(), 10) + radius, Util.dp(getContext(), -13) + radius), paint);
        }
    }

}
