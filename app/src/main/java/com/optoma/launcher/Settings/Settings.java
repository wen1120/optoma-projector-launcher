package com.optoma.launcher.Settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.optoma.launcher.R;

import java.util.ArrayList;

public class Settings extends Activity {
    private static final String TAG = "LauncherLog";
    private static final int xLimit = 3, yLimit = 3;
    private int xPosition, yPosition, xFocus, yFocus;
    private TextView SettingTV;
    private TextView[] SystemTitleTV  = new TextView[3];
    private TextView[] SystemContentTV  = new TextView[3];
    private ArrayList<String> aSystemTitle = new ArrayList<String>();
    private ArrayList<String> aSystemContent = new ArrayList<String>();
    private Intent intent = new Intent();
    public static boolean[] bControlItems = {true,false,true,false,false,true};
    public String[] bControlString = {"Crestron","Extron","PJ Link","AMX","Telnet","HTTP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        xPosition = yPosition = xFocus = yFocus = 0;
        SettingTV = (TextView) findViewById(R.id.setting_textview);
        SystemTitleTV[0] = (TextView) findViewById(R.id.setting_tv_upper_title);
        SystemTitleTV[1] = (TextView) findViewById(R.id.setting_tv_middle_title);
        SystemTitleTV[2] = (TextView) findViewById(R.id.setting_tv_lower_title);
        SystemContentTV[0] = (TextView) findViewById(R.id.setting_tv_upper_content);
        SystemContentTV[1] = (TextView) findViewById(R.id.setting_tv_middle_content);
        SystemContentTV[2] = (TextView) findViewById(R.id.setting_tv_lower_content);
        for (String s : getResources().getStringArray(
                R.array.settingTitle)) {
            aSystemTitle.add(s);
        }
        for (String s : getResources().getStringArray(
                R.array.settingContent)) {
            aSystemContent.add(s);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SetSettingTV(true);
    }
    public void SettingClick(View view) {
        SetSettingTV(false);
        switch (view.getId()) {
            case R.id.setting_audio_button:
                intent.setClassName(getPackageName(), getPackageName() + ".Settings.Audio");
                break;
            case R.id.setting_control_button:
                intent.setClassName(getPackageName(), getPackageName() + ".Settings.Control");
                break;
            case R.id.setting_information_button:
                intent.setClassName(getPackageName(), getPackageName() + ".Settings.Information");
                break;
            case R.id.setting_system_button:
                intent.setClassName(getPackageName(), getPackageName() + ".Settings.System");
                break;
            default:
                Log.d(TAG, "unknow setting id=" + view.getId());
                break;
        }
        startActivity(intent);
    }

    private void SetSettingTV(boolean show) {
        int iShow = show ? View.VISIBLE : View.INVISIBLE;
        if((2 == xFocus) && (2 == yFocus)) {
            String sControl = "";
            boolean bStart = true;
            for(int i = 0;i < 6;i++) {
                if(bControlItems[i]) {
                    if(false == bStart) {
                        sControl = sControl + ", ";
                    }
                    sControl = sControl + bControlString[i];
                    bStart = false;
                }
            }
            aSystemContent.set(8, sControl);
            SetTitleContent(xFocus,yFocus);
        }
        SettingTV.setVisibility(iShow);
        SystemTitleTV[yFocus].setVisibility(iShow);
        SystemContentTV[yFocus].setVisibility(iShow);
    }

    public void SetTitleContent(int x, int y) {
        for(int i=0;i<3;i++) {
            if(i != y) {
                SystemTitleTV[i].setVisibility(View.INVISIBLE);
                SystemContentTV[i].setVisibility(View.INVISIBLE);
            } else {
                SystemTitleTV[i].setText(aSystemTitle.get(3*y + x));
                SystemContentTV[i].setText(aSystemContent.get(3*y + x));
                SystemTitleTV[i].setVisibility(View.VISIBLE);
                SystemContentTV[i].setVisibility(View.VISIBLE);
                xFocus = x;
                yFocus = y;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if(yPosition > 0) {
                    yPosition--;
                    SetTitleContent(xPosition,yPosition);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if(yPosition < yLimit - 1) {
                    yPosition++;
                    SetTitleContent(xPosition,yPosition);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if(xPosition > 0) {
                    xPosition--;
                    SetTitleContent(xPosition,yPosition);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if(xPosition < xLimit - 1) {
                    xPosition++;
                    SetTitleContent(xPosition,yPosition);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

}