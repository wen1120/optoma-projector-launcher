package com.optoma.launcher.ui;

import android.content.Context;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.optoma.launcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeekBarController implements ViewController {

    private View view;

    @BindView(R.id.title) TextView title;
    @BindView(R.id.value) TextView value;
    @BindView(R.id.seekbar)
    android.widget.SeekBar seekBar;
    @BindView(R.id.left_arrow) View leftButton;
    @BindView(R.id.right_arrow) View rightButton;

    public SeekBarController(Context context,
                             final String title, final int initial, final int min, final int max, final int step) {
        view = View.inflate(context, R.layout.menu_seekbar, null);
        ButterKnife.bind(this, view);

        this.title.setText(title);

        seekBar.setMax(max - min);
        seekBar.setProgress(initial - min);
        seekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
                int correctedProgress = progress + min;
                value.setText(String.valueOf(correctedProgress));
            }

            @Override
            public void onStartTrackingTouch(android.widget.SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(android.widget.SeekBar seekBar) {

            }
        });
        value.setText(String.valueOf(seekBar.getProgress() + min));

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.incrementProgressBy(-step);
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.incrementProgressBy(step);
            }
        });
    }

    public View getView() {
        return view;
    }

}
