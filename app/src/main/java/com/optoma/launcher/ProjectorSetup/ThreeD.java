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

public class ThreeD extends Activity {
    private static final String TAG = "LauncherLog";
    private int xPosition, yPosition;
    private final int yLimit = 5, TotalIV = 1, TotalTV = 4;
    private boolean[] b3DOn = {false};
    private ArrayList<String> a3DMode = new ArrayList<String>();
    private ArrayList<String> a3D2D = new ArrayList<String>();
    private ArrayList<String> a3DFormat = new ArrayList<String>();
    private ArrayList<String> a2D3D = new ArrayList<String>();
    private ImageView[] iv3DOnOff = new ImageView[TotalIV];
    private TextView[] tv3DContent = new TextView[TotalTV];
    private int[] iArrayIndex = {0,0,0,0};
    private static int[] ThreeDOnID = {
            R.id.threed_3d_sync_invert_on
    };
    private static int[] ThreeDTVID = {
            R.id.threed_3d_mode_content_tv,
            R.id.threed_3d_2d_content_tv,
            R.id.threed_3d_format_content_tv,
            R.id.threed_2d_3d_content_tv
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.osd_threed);
        for(int i=0;i<TotalTV;i++) {
            tv3DContent[i] = (TextView) this.findViewById(ThreeDTVID[i]);
            if(i<TotalIV) iv3DOnOff[i] = (ImageView) this.findViewById(ThreeDOnID[i]);
        }
        for (String s : getResources().getStringArray(
                R.array.threed_3d_mode_array)) {
            a3DMode.add(s);
        }
        for (String s : getResources().getStringArray(
                R.array.threed_3d_2d_array)) {
            a3D2D.add(s);
        }
        for (String s : getResources().getStringArray(
                R.array.threed_3d_format_array)) {
            a3DFormat.add(s);
        }
        for (String s : getResources().getStringArray(
                R.array.threed_2d_3d_array)) {
            a2D3D.add(s);
        }
        xPosition = yPosition = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void SetOnOff(int index) {
        b3DOn[index] = !b3DOn[index];
        int ImageSource = b3DOn[index] ? R.drawable.on : R.drawable.off;
        iv3DOnOff[index].setImageResource(ImageSource);
    }

    private void SetArrayValue(int keycode, ArrayList<String> array, int index) {
        if(KeyEvent.KEYCODE_DPAD_LEFT == keycode) {
            iArrayIndex[index] = iArrayIndex[index] > 0 ? iArrayIndex[index] - 1 : array.size() - 1;
        } else if(KeyEvent.KEYCODE_DPAD_RIGHT == keycode) {
            iArrayIndex[index] = iArrayIndex[index] < array.size() - 1 ? iArrayIndex[index] + 1 : 0;
        }
        tv3DContent[index].setText(array.get(iArrayIndex[index]));
    }
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction()!=KeyEvent.ACTION_DOWN)
            return super.dispatchKeyEvent(event);
        switch(event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
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
            case KeyEvent.KEYCODE_ENTER:
                switch (yPosition) {
                    case 3:
                        SetOnOff(0);
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                switch (yPosition) {
                    case 0:
                        SetArrayValue(event.getKeyCode(), a3DMode, 0);
                        break;
                    case 1:
                        SetArrayValue(event.getKeyCode(), a3D2D, 1);
                        break;
                    case 2:
                        SetArrayValue(event.getKeyCode(), a3DFormat, 2);
                        break;
                    case 3:
                        SetOnOff(0);
                        break;
                    case 4:
                        SetArrayValue(event.getKeyCode(), a2D3D, 3);
                        break;
                }
                break;
        }
        return super.dispatchKeyEvent(event);
    }
}