package com.optoma.launcher.Settings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.optoma.launcher.R;


public class General extends Activity {
    private static final String TAG = "LauncherLog";
    private int xPosition, yPosition, iScreenTimeoutIndex;
    private final int yLimit = 5;
    private boolean[] bGeneralItems = {true,false};
    private int[] iGeneralChoice = {0,0};
    private ImageView[] ivGeneralOnOff = new ImageView[2];
    private TextView[] tvGeneral = new TextView[3];

    public String[] sScreenTimeout = {
            "Never",
            "2 minutes",
            "5 minutes",
            "10 minutes",
            "15 minutes",
            "30 minutes"
    };

    private static int[] GeneralOnOffID = {
            R.id.general_send_log_iv,
            R.id.general_closed_captioning_iv
    };
    private static int[] GeneralTVID = {
            R.id.general_config_projector_id_content_tv,
            R.id.general_config_remote_code_content_tv,
            R.id.general_screen_timeout_content_tv
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        for(int i=0;i< tvGeneral.length; i++) {
            tvGeneral[i] = (TextView) this.findViewById(GeneralTVID[i]);
            if(i<ivGeneralOnOff.length)
                ivGeneralOnOff[i] = (ImageView) this.findViewById(GeneralOnOffID[i]);
        }
        xPosition = yPosition = iScreenTimeoutIndex = 0;
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
                switch (yPosition) {
                    case 0:
                    case 1:
                        if(KeyEvent.KEYCODE_ENTER == keyCode) break;
                        else if(KeyEvent.KEYCODE_DPAD_LEFT == keyCode) {
                            iGeneralChoice[yPosition] = iGeneralChoice[yPosition] > 0 ? iGeneralChoice[yPosition] - 1 : 99;
                        } else if(KeyEvent.KEYCODE_DPAD_RIGHT == keyCode) {
                            iGeneralChoice[yPosition] = iGeneralChoice[yPosition] < 99 ? iGeneralChoice[yPosition] + 1 : 0;
                        }
                        tvGeneral[yPosition].setText(iGeneralChoice[yPosition] < 10 ? new String("0"+iGeneralChoice[yPosition]):Integer.toString(iGeneralChoice[yPosition]));
                        break;
                    case 2:
                        if(KeyEvent.KEYCODE_ENTER == keyCode) break;
                        else if(KeyEvent.KEYCODE_DPAD_LEFT == keyCode) {
                            iScreenTimeoutIndex = iScreenTimeoutIndex > 0 ? iScreenTimeoutIndex - 1 : sScreenTimeout.length - 1;
                        } else if(KeyEvent.KEYCODE_DPAD_RIGHT == keyCode) {
                            iScreenTimeoutIndex = iScreenTimeoutIndex < sScreenTimeout.length - 1? iScreenTimeoutIndex + 1 : 0;
                        }
                        tvGeneral[yPosition].setText(sScreenTimeout[iScreenTimeoutIndex]);
                        break;
                    case 3:
                    case 4:
                        bGeneralItems[yPosition-3] = !bGeneralItems[yPosition-3];
                        ImageSource = bGeneralItems[yPosition-3] ? R.drawable.on : R.drawable.off;
                        ivGeneralOnOff[yPosition-3].setImageResource(ImageSource);
                        break;
                }
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
}