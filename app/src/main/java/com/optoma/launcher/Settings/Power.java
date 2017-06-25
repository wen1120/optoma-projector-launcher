package com.optoma.launcher.Settings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.optoma.launcher.R;


public class Power extends Activity {
    private static final String TAG = "launcherLog";
    private int xPosition, yPosition;
    private final int yLimit = 11, TotalIV = 5, TotalTV = 2;
    private boolean[] bPowerItems = {true,false,false,false,false};
    private int[] iPowerTimer = {0, 0};
    private ImageView[] ivPowerOnOff = new ImageView[TotalIV];
    private TextView[] tvPowerTimer = new TextView[TotalTV];
    private static int[] PowerIVID = {
            R.id.power_direct_poweron_iv,
            R.id.power_signal_poweron_iv,
            R.id.power_sleep_iv,
            R.id.power_quick_resume_iv,
            R.id.power_wireless_iv
    };
    private static int[] PowerTVID = {
            R.id.power_auto_poweroff_content_tv,
            R.id.power_sleep_timer_content_tv
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power);
        for(int i=0;i<TotalIV;i++) {
            ivPowerOnOff[i] = (ImageView) this.findViewById(PowerIVID[i]);
            if(i<TotalTV) tvPowerTimer[i] = (TextView) this.findViewById(PowerTVID[i]);
        }
        xPosition = yPosition = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void SetOnOff(int index) {
        bPowerItems[index] = !bPowerItems[index];
        int ImageSource = bPowerItems[index] ? R.drawable.on : R.drawable.off;
        ivPowerOnOff[index].setImageResource(ImageSource);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if(yPosition > 0) {
                    yPosition--;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if(yPosition < yLimit - 1) {
                    yPosition++;
                }
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
            case KeyEvent.KEYCODE_ENTER:
                switch (yPosition) {
                    case 2:
                        SetOnOff(0);
                        break;
                    case 3:
                        SetOnOff(1);
                        break;
                    case 5:
                        SetOnOff(2);
                        break;
                    case 7:
                        SetOnOff(3);
                        break;
                    case 10:
                        SetOnOff(4);
                        break;
                    case 4:
                        if(KeyEvent.KEYCODE_ENTER == keyCode) break;
                        else if(KeyEvent.KEYCODE_DPAD_LEFT == keyCode) {
                            iPowerTimer[0] = iPowerTimer[0] > 0 ? iPowerTimer[0] - 5 : 180;
                        } else if(KeyEvent.KEYCODE_DPAD_RIGHT == keyCode) {
                            iPowerTimer[0] = iPowerTimer[0] < 180 ? iPowerTimer[0] + 5 : 0;
                        }
                        tvPowerTimer[0].setText(Integer.toString(iPowerTimer[0]));
                        break;
                    case 6:
                        if(KeyEvent.KEYCODE_ENTER == keyCode) break;
                        else if(KeyEvent.KEYCODE_DPAD_LEFT == keyCode) {
                            iPowerTimer[1] = iPowerTimer[1] > 0 ? iPowerTimer[1] - 30 : 990;
                        } else if(KeyEvent.KEYCODE_DPAD_RIGHT == keyCode) {
                            iPowerTimer[1] = iPowerTimer[1] < 990 ? iPowerTimer[1] + 30 : 0;
                        }
                        tvPowerTimer[1].setText(Integer.toString(iPowerTimer[1]));
                        break;
                }
                break;
        }
        Log.d(TAG, "yPosition =  " + yPosition);
        return super.onKeyDown(keyCode, event);
    }
}
