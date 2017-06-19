package com.optoma.launcher.ui;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.optoma.launcher.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeekBarController extends ViewController {

    @BindView(R.id.title) TextView title;
    @BindView(R.id.value) TextView value;
    @BindView(R.id.seekbar)
    android.widget.SeekBar seekBar;
    @BindView(R.id.left_arrow) View leftButton;
    @BindView(R.id.right_arrow) View rightButton;

    private final List<SeekBar.OnSeekBarChangeListener> seekBarChangeListeners = new ArrayList<>();

    public SeekBarController(Context context,
                             final String title, final int initial, final int min, final int max, final int step) {
        super(View.inflate(context, R.layout.menu_seekbar, null));
        ButterKnife.bind(this, view);

        this.title.setText(title);

        seekBar.setMax(max - min);
        seekBar.setProgress(initial - min);
        seekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
                int correctedProgress = progress + min;
                value.setText(String.valueOf(correctedProgress));

                for(SeekBar.OnSeekBarChangeListener lis : seekBarChangeListeners) {
                    lis.onProgressChanged(seekBar, correctedProgress, fromUser);
                }
            }

            @Override
            public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
                for(SeekBar.OnSeekBarChangeListener lis : seekBarChangeListeners) {
                    lis.onStartTrackingTouch(seekBar);
                }
            }

            @Override
            public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
                for(SeekBar.OnSeekBarChangeListener lis : seekBarChangeListeners) {
                    lis.onStopTrackingTouch(seekBar);
                }
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

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() != KeyEvent.ACTION_DOWN) return false;
                if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    seekBar.incrementProgressBy(-step);
                } else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    seekBar.incrementProgressBy(step);
                } else {
                    return false;
                }
                return true;
            }
        });
    }

    public void addOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener lis) {
        seekBarChangeListeners.add(lis);
    }
}
