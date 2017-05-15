package com.optoma.launcher.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.optoma.launcher.R;

public class OSeekBar extends LinearLayout {

    private Slider seekBar;
    private ImageView left;
    private ImageView right;

    public OSeekBar(Context context) {
        super(context);

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);

        seekBar = new Slider(context);

        seekBar.setLayoutParams(OUtil.getWeightedLayoutParams());

        left = new ImageButton(context);
        left.setBackgroundColor(Color.TRANSPARENT);
        left.setImageResource(R.drawable.left_arrow427);

        right = new ImageButton(context);
        right.setBackgroundColor(Color.TRANSPARENT);
        right.setImageResource(R.drawable.right_arrow427);

        addView(left);
        addView(seekBar);
        addView(right);
    }


    static class Slider extends View {

        private final Paint paint = new Paint();

        public Slider(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawLine(OUtil.dpToPixel(getContext(), 0), getHeight()/2, getWidth(), getHeight()/2, paint);
        }
    }
}
