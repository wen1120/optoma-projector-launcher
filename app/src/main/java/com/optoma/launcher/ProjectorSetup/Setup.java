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


public class Setup extends Activity {
    private static final String TAG = "LauncherLog";
    private int xPosition, yPosition,iCeilingMount,iTestPattern;
    private final int yLimit = 4, TotalIV = 1, TotalTV = 2, TotalV = 1;
    private boolean[] bSetupOn = {false};
    private ArrayList<String> aSetupCeilingMount = new ArrayList<String>();
    private ArrayList<String> aSetupTestPattern = new ArrayList<String>();
    private ImageView[] ivSetupOnOff = new ImageView[TotalIV];
    private TextView[] tvSetupContent = new TextView[TotalTV];
    private View[] vSetupBigView = new View[TotalV];
    private static int[] SetupOnID = {
            R.id.setup_rear_projection_on
    };
    private static int[] SetupTVID = {
            R.id.setup_ceiling_mount_content_tv,
            R.id.setup_test_pattern_content_tv
    };
    private static int[] SetupVID = {
            R.id.setup_position_view
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.osd_setup);
        for(int i=0;i<TotalTV;i++) {
            tvSetupContent[i] = (TextView) this.findViewById(SetupTVID[i]);
            if(i<TotalIV) ivSetupOnOff[i] = (ImageView) this.findViewById(SetupOnID[i]);
            if(i<TotalV) vSetupBigView[i] = this.findViewById(SetupVID[i]);
        }
        for (String s : getResources().getStringArray(
                R.array.setup_ceiling_mount_array)) {
            aSetupCeilingMount.add(s);
        }
        for (String s : getResources().getStringArray(
                R.array.setup_test_pattern_array)) {
            aSetupTestPattern.add(s);
        }
        xPosition = yPosition = iCeilingMount = iTestPattern = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void SetOnOff(int index) {
        bSetupOn[index] = !bSetupOn[index];
        int ImageSource = bSetupOn[index] ? R.drawable.on : R.drawable.off;
        ivSetupOnOff[index].setImageResource(ImageSource);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if(yPosition == 3) {
                    vSetupBigView[0].setVisibility(View.VISIBLE);
                    yPosition--;
                }
                else if(yPosition > 0) {
                    yPosition--;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if(yPosition == 2) {
                    vSetupBigView[0].setVisibility(View.INVISIBLE);
                    yPosition++;
                }
                else if(yPosition < yLimit - 1) {
                    yPosition++;
                }
                break;
            case KeyEvent.KEYCODE_ENTER:
                switch (yPosition) {
                    case 2:
                        SetOnOff(0);
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                switch (yPosition) {
                    case 0:
                        break;
                    case 1:
                        if(KeyEvent.KEYCODE_DPAD_LEFT == keyCode) {
                            iCeilingMount = iCeilingMount > 0 ? iCeilingMount - 1 : aSetupCeilingMount.size() - 1;
                        } else if(KeyEvent.KEYCODE_DPAD_RIGHT == keyCode) {
                            iCeilingMount = iCeilingMount < aSetupCeilingMount.size() - 1 ? iCeilingMount + 1 : 0;
                        }
                        tvSetupContent[0].setText(aSetupCeilingMount.get(iCeilingMount));
                        break;
                    case 2:
                        SetOnOff(0);
                        break;
                    case 3:
                        if(KeyEvent.KEYCODE_DPAD_LEFT == keyCode) {
                            iTestPattern = iTestPattern > 0 ? iTestPattern - 1 : aSetupTestPattern.size() - 1;
                        } else if(KeyEvent.KEYCODE_DPAD_RIGHT == keyCode) {
                            iTestPattern = iTestPattern < aSetupTestPattern.size() - 1 ? iTestPattern + 1 : 0;
                        }
                        tvSetupContent[1].setText(aSetupTestPattern.get(iTestPattern));
                        break;
                }
                break;
        }
        Log.d(TAG, "yPosition =  " + yPosition + ",keycode = " + keyCode);
        return super.onKeyDown(keyCode, event);
    }
}