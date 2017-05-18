package com.optoma.launcher.ProjectorSetup;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.optoma.launcher.R;

import java.util.ArrayList;


public class SourceControl extends Activity {
    private static final String TAG = "LauncherLog";
    private int xPosition, yPosition,iPowerOnLink;
    private final int yLimit = 6, TotalIV = 5, TotalTV = 1, TotalV = 2;
    private boolean[] bSCOn = {true,false,false,true,true};
    private ArrayList<String> aSCPowerOnLink = new ArrayList<String>();
    private ImageView[] ivSCOnOff = new ImageView[TotalIV];
    private TextView[] tvSCContent = new TextView[TotalTV];
    private View[] vSCBigView = new View[TotalV];
    private static int[] SCOnID = {
            R.id.sc_hdmi_link_on,
            R.id.sc_inclusive_of_tv_on,
            R.id.sc_power_off_link_on,
            R.id.sc_ethernet_on,
            R.id.sc_rs232_on
    };
    private static int[] SCTVID = {
            R.id.sc_power_on_link_content_tv
    };
    private static int[] SCVID = {
            R.id.sc_hdmi_link_settings_view,
            R.id.sc_hdbaset_control_view
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.osd_source_control);
        for(int i=0;i<TotalIV;i++) {
            ivSCOnOff[i] = (ImageView) this.findViewById(SCOnID[i]);
            if(i<TotalTV) tvSCContent[i] = (TextView) this.findViewById(SCTVID[i]);
            if(i<TotalV) vSCBigView[i] = this.findViewById(SCVID[i]);
        }
        for (String s : getResources().getStringArray(
                R.array.SC_power_on_link_array)) {
            aSCPowerOnLink.add(s);
        }
        xPosition = yPosition = iPowerOnLink = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void SetOnOff(int index) {
        bSCOn[index] = !bSCOn[index];
        int ImageSource = bSCOn[index] ? R.drawable.on : R.drawable.off;
        ivSCOnOff[index].setImageResource(ImageSource);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_DOWN)
            return super.dispatchKeyEvent(event);
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if(yPosition == 4) {
                    vSCBigView[0].setVisibility(View.VISIBLE);
                    vSCBigView[1].setVisibility(View.INVISIBLE);
                    yPosition--;
                }
                else if(yPosition > 0) {
                    yPosition--;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if(yPosition == 3) {
                    vSCBigView[0].setVisibility(View.INVISIBLE);
                    vSCBigView[1].setVisibility(View.VISIBLE);
                    yPosition++;
                }
                else if(yPosition < yLimit - 1) {
                    yPosition++;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
            case KeyEvent.KEYCODE_ENTER:
                switch (yPosition) {
                    case 0:
                        SetOnOff(0);
                        break;
                    case 1:
                        SetOnOff(1);
                        break;
                    case 3:
                        SetOnOff(2);
                        break;
                    case 4:
                        SetOnOff(3);
                        break;
                    case 5:
                        SetOnOff(4);
                        break;
                    case 2:
                        if(KeyEvent.KEYCODE_ENTER == event.getKeyCode()) break;
                        else if(KeyEvent.KEYCODE_DPAD_LEFT == event.getKeyCode()) {
                            iPowerOnLink = iPowerOnLink > 0 ? iPowerOnLink - 1 : aSCPowerOnLink.size() - 1;
                        } else if(KeyEvent.KEYCODE_DPAD_RIGHT == event.getKeyCode()) {
                            iPowerOnLink = iPowerOnLink < aSCPowerOnLink.size() - 1 ? iPowerOnLink + 1 : 0;
                        }
                        tvSCContent[0].setText(aSCPowerOnLink.get(iPowerOnLink));
                        break;
                }
                break;
        }
        return super.dispatchKeyEvent(event);
    }
}