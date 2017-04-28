package com.optoma.launcher.Settings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.optoma.launcher.R;

public class Control extends Activity {
    private static final String TAG = "LauncherLog";
    private int xPosition, yPosition;
    private final int yLimit = 6;
    private ImageView[] ivControlOnOff = new ImageView[yLimit];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        ivControlOnOff[0] = (ImageView) this.findViewById(R.id.control_crestron_iv);
        ivControlOnOff[1] = (ImageView) this.findViewById(R.id.control_extron_iv);
        ivControlOnOff[2] = (ImageView) this.findViewById(R.id.control_pjlink_iv);
        ivControlOnOff[3] = (ImageView) this.findViewById(R.id.control_amxdd_iv);
        ivControlOnOff[4] = (ImageView) this.findViewById(R.id.control_telnet_iv);
        ivControlOnOff[5] = (ImageView) this.findViewById(R.id.control_http_iv);
        for(int i = 0; i< yLimit;i++) {
            int ImageSource = Settings.bControlItems[i] ? R.drawable.on : R.drawable.off;
            ivControlOnOff[i].setImageResource(ImageSource);
        }
        xPosition = yPosition = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int ImageSource = 0;
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
                Settings.bControlItems[yPosition] = !Settings.bControlItems[yPosition];
                ImageSource = Settings.bControlItems[yPosition] ? R.drawable.on : R.drawable.off;
                ivControlOnOff[yPosition].setImageResource(ImageSource);
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

}