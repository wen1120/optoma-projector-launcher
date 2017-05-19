package com.optoma.launcher.ProjectorSetup;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.optoma.launcher.R;

import java.util.ArrayList;


public class Signal extends Activity {
    private static final String TAG = "LauncherLog";
    private int xPosition, yPosition,iColorSpace,iIRE;
    private final int yLimit = 12, TotalIV = 1, TotalTV = 2, TotalSB = 8;
    private boolean[] bSignalOn = {false};
    private ArrayList<String> aSignalColorSpace = new ArrayList<String>();
    private ArrayList<String> aSignalIRE = new ArrayList<String>();
    private SeekBar[] sbSignal = new SeekBar[TotalSB];
    private int[] iSignalSB = {50,15,50,50,50,50,50,50};
    private int[] iSignalSBMax = {100,31,100,100,100,100,100,100};
    private TextView[] tvSignalSBValue = new TextView[TotalSB];
    private ImageView[] ivSignalOnOff = new ImageView[TotalIV];
    private TextView[] tvSignalContent = new TextView[TotalTV];
    private View vStartView,vEndView;
    private static int[] SignalOnID = {
            R.id.signal_automatic_on
    };
    private static int[] SignalTVID = {
            R.id.signal_color_space_content_tv,
            R.id.signal_ire_content_tv
    };
    private static int[] SignalSBID = {
            R.id.signal_frequency_sb,
            R.id.signal_phase_sb,
            R.id.signal_hposition_sb,
            R.id.signal_vposition_sb,
            R.id.signal_white_level_sb,
            R.id.signal_black_level_sb,
            R.id.signal_saturation_sb,
            R.id.signal_hue_sb
    };
    private static int[] SignalSBValueID = {
            R.id.signal_frequency_value_tv,
            R.id.signal_phase_value_tv,
            R.id.signal_hposition_value_tv,
            R.id.signal_vposition_value_tv,
            R.id.signal_white_level_value_tv,
            R.id.signal_black_level_value_tv,
            R.id.signal_saturation_value_tv,
            R.id.signal_hue_value_tv
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.osd_signal);
        for(int i=0;i<TotalSB;i++) {
            sbSignal[i] = (SeekBar) this.findViewById(SignalSBID[i]);
            tvSignalSBValue[i] = (TextView) this.findViewById(SignalSBValueID[i]);
            if(i<TotalIV) ivSignalOnOff[i] = (ImageView) this.findViewById(SignalOnID[i]);
            if(i<TotalTV) tvSignalContent[i] = (TextView) this.findViewById(SignalTVID[i]);
        }
        for (String s : getResources().getStringArray(
                R.array.signal_color_space_array)) {
            aSignalColorSpace.add(s);
        }
        for (String s : getResources().getStringArray(
                R.array.signal_ire_array)) {
            aSignalIRE.add(s);
        }
        vStartView = this.findViewById(R.id.signal_automatic_view);
        vEndView = this.findViewById(R.id.signal_back_projector_view);
        xPosition = yPosition = iColorSpace = iIRE = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void SetOnOff(int index) {
        bSignalOn[index] = !bSignalOn[index];
        int ImageSource = bSignalOn[index] ? R.drawable.on : R.drawable.off;
        ivSignalOnOff[index].setImageResource(ImageSource);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction()!=KeyEvent.ACTION_DOWN)
            return super.dispatchKeyEvent(event);
        switch(event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                yPosition = yPosition > 0 ? yPosition - 1: yLimit - 1;
                if(yPosition == yLimit - 1) {
                    vEndView.requestFocus();
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                yPosition = yPosition < yLimit - 1 ? yPosition + 1: 0;
                if(yPosition == 0) {
                    vStartView.requestFocus();
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_ENTER:
                switch (yPosition) {
                    case 0:
                        SetOnOff(0);
                        break;
                    case yLimit - 1:
                        finish();
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                switch (yPosition) {
                    case 0:
                        SetOnOff(0);
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
                            if(iSignalSB[yPosition-1] > 0) {
                                iSignalSB[yPosition - 1]--;
                            }
                        } else {
                            if(iSignalSB[yPosition-1] < iSignalSBMax[yPosition-1]) {
                                iSignalSB[yPosition-1]++;
                            }
                        }
                        sbSignal[yPosition-1].setProgress(iSignalSB[yPosition-1]);
                        tvSignalSBValue[yPosition-1].setText(Integer.toString(yPosition == 2 ? iSignalSB[yPosition-1] : iSignalSB[yPosition-1] - 50));
                        break;
                    case 5:
                        if(KeyEvent.KEYCODE_DPAD_LEFT == event.getKeyCode()) {
                            iColorSpace = iColorSpace > 0 ? iColorSpace - 1 : aSignalColorSpace.size() - 1;
                        } else if(KeyEvent.KEYCODE_DPAD_RIGHT == event.getKeyCode()) {
                            iColorSpace = iColorSpace < aSignalColorSpace.size() - 1 ? iColorSpace + 1 : 0;
                        }
                        tvSignalContent[0].setText(aSignalColorSpace.get(iColorSpace));
                        break;
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
                            if(iSignalSB[yPosition-2] > 0) {
                                iSignalSB[yPosition-2]--;
                            }
                        } else {
                            if(iSignalSB[yPosition-2] < iSignalSBMax[yPosition-2]) {
                                iSignalSB[yPosition-2]++;
                            }
                        }
                        sbSignal[yPosition-2].setProgress(iSignalSB[yPosition-2]);
                        tvSignalSBValue[yPosition-2].setText(Integer.toString(iSignalSB[yPosition-2] - 50));
                        break;
                    case 10:
                        if(KeyEvent.KEYCODE_DPAD_LEFT == event.getKeyCode()) {
                            iIRE = iIRE > 0 ? iIRE - 1 : aSignalIRE.size() - 1;
                        } else if(KeyEvent.KEYCODE_DPAD_RIGHT == event.getKeyCode()) {
                            iIRE = iIRE < aSignalIRE.size() - 1 ? iIRE + 1 : 0;
                        }
                        tvSignalContent[1].setText(aSignalIRE.get(iIRE));
                        break;
                }
                break;
        }
        return super.dispatchKeyEvent(event);
    }

}