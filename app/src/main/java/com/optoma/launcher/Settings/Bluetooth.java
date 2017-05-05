package com.optoma.launcher.Settings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.optoma.launcher.R;


public class Bluetooth extends Activity {
    private static final String TAG = "LauncherLog";
    private final int yLimit = 2;
    private int xPosition, yPosition;
    private boolean bBTPower = false;
    private ImageView ivBTOnOff;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        xPosition = yPosition = 0;
        ivBTOnOff = (ImageView) this.findViewById(R.id.bluetooth_onoff_iv);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                    case 0:
                        bBTPower = !bBTPower;
                        int ImageSource = bBTPower ? R.drawable.on : R.drawable.off;
                        ivBTOnOff.setImageResource(ImageSource);
                        break;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}