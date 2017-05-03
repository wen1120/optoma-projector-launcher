package com.optoma.launcher.Settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.optoma.launcher.R;

import java.util.ArrayList;

public class System extends Activity {
    private static final String TAG = "LauncherLog";
    private int xPosition, yPosition, iPosition = 0;
    private final int yLimit = 9, UpdateCount = 3;
    private boolean[] bSystemItems = {true,false};
    private View vDateTime, vSecurity,vSetTime,vSecurityTimer,vChangePW,vMask;
    private ImageView[] ivSystemOnOff = new ImageView[2];
    private Intent intent = new Intent();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);
        vDateTime = findViewById(R.id.system_date_time_main_view);
        vSecurity = findViewById(R.id.system_security_main_view);
        vSetTime = findViewById(R.id.system_set_time_view);
        vSecurityTimer = findViewById(R.id.system_security_timer_view);
        vChangePW = findViewById(R.id.system_change_pw_view);
        vMask = findViewById(R.id.system_mask_view);
        ivSystemOnOff[0] = (ImageView) this.findViewById(R.id.system_automatic_iv);
        ivSystemOnOff[1] = (ImageView) this.findViewById(R.id.system_security_iv);
        xPosition = yPosition = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        vMask.setVisibility(View.INVISIBLE);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int ImageSource = 0;
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                switch (yPosition) {
                    case 1:
                        vDateTime.setVisibility(View.INVISIBLE);
                        yPosition--;
                        break;
                    case 3:
                        vSecurity.setVisibility(View.INVISIBLE);
                        vDateTime.setVisibility(View.VISIBLE);
                        yPosition = bSystemItems[0] ? yPosition - 2 : yPosition - 1;
                        break;
                    case 6:
                        vSecurity.setVisibility(View.VISIBLE);
                        yPosition = bSystemItems[1] ? yPosition - 1 : yPosition - 3;
                        break;
                    default:
                        if(yPosition > 0) {
                            yPosition--;
                        }
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                switch (yPosition) {
                    case 0:
                        vDateTime.setVisibility(View.VISIBLE);
                        yPosition++;
                        break;
                    case 1:
                        if(bSystemItems[0]) {
                            vDateTime.setVisibility(View.INVISIBLE);
                            vSecurity.setVisibility(View.VISIBLE);
                            yPosition += 2;
                        } else {
                            yPosition++;
                        }
                        break;
                    case 2:
                        vDateTime.setVisibility(View.INVISIBLE);
                        vSecurity.setVisibility(View.VISIBLE);
                        yPosition++;
                        break;
                    case 3:
                        if(!bSystemItems[1]) {
                            vSecurity.setVisibility(View.INVISIBLE);
                            yPosition += 3;
                        } else {
                            yPosition++;
                        }
                        break;
                    case 5:
                        vSecurity.setVisibility(View.INVISIBLE);
                        yPosition++;
                        break;
                    default:
                        if(yPosition < yLimit - 1) {
                            yPosition++;
                        }
                        break;
                }
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                switch (yPosition) {
                    case 1:
                    case 3:
                        int yIndex = yPosition==1 ? 0 : 1;
                        bSystemItems[yIndex] = !bSystemItems[yIndex];
                        if(0 == yIndex) {
                            vSetTime.setFocusable(!bSystemItems[yIndex]);
                        } else {
                            vSecurityTimer.setFocusable(bSystemItems[yIndex]);
                            vChangePW.setFocusable(bSystemItems[yIndex]);
                        }
                        ImageSource = bSystemItems[yIndex] ? R.drawable.on : R.drawable.off;
                        ivSystemOnOff[yIndex].setImageResource(ImageSource);
                        Log.d(TAG, "yIndex =  " + yIndex + ",bSystemItems[yIndex] = " + bSystemItems[yIndex] );
                        break;
                    default:
                        Log.d(TAG, "get left right key, yPosition =  " + yPosition);
                        break;
                }
                break;
            case KeyEvent.KEYCODE_ENTER:
                switch (yPosition) {
                    case 1:
                    case 3:
                        int yIndex = yPosition==1 ? 0 : 1;
                        bSystemItems[yIndex] = !bSystemItems[yIndex];
                        if(0 == yIndex) {
                            vSetTime.setFocusable(!bSystemItems[yIndex]);
                        } else {
                            vSecurityTimer.setFocusable(bSystemItems[yIndex]);
                            vChangePW.setFocusable(bSystemItems[yIndex]);
                        }
                        ImageSource = bSystemItems[yIndex] ? R.drawable.on : R.drawable.off;
                        ivSystemOnOff[yIndex].setImageResource(ImageSource);
                        Log.d(TAG, "yIndex =  " + yIndex + ",bSystemItems[yIndex] = " + bSystemItems[yIndex] );
                        break;
                    case 2:
                        vMask.setVisibility(View.VISIBLE);
                        //intent.setClassName(getPackageName(), getPackageName() + ".Settings.SystemSetTime");
                        //startActivity(intent);
                        break;
                    case 6:
                        OTAUpdate();
                        break;
                }
                break;
        }
        Log.d(TAG, "yPosition =  " + yPosition);
        return super.onKeyDown(keyCode, event);
    }

    protected void OTAUpdate() {
        iPosition = 0;
        final View UpdateView = LayoutInflater.from(System.this).inflate(R.layout.update_layout, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(System.this, R.style.CustomAlertDialog);
        alertDialog.setView(UpdateView);
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                boolean bFocus = true;
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;
                if (true == bFocus) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            dialog.dismiss();
                            break;
                        case KeyEvent.KEYCODE_DPAD_UP:
                            if(iPosition > 0)iPosition--;
                            Log.d(TAG, "iPosition =  " + iPosition);
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            if(iPosition < UpdateCount - 1)iPosition++;
                            Log.d(TAG, "iPosition =  " + iPosition);
                            break;
                    }
                }
                return false;
            }
        });

        alertDialog.show();
    }
}